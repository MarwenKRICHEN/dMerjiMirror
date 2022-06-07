package com.example.dmerjimirror.ui.main.overview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.dmerjimirror.MainActivity
import com.example.dmerjimirror.R
import com.example.dmerjimirror.adapater.SmallComponentAdapter
import com.example.dmerjimirror.databinding.FragmentOverviewBinding
import com.example.dmerjimirror.library.controller.UserController
import com.example.dmerjimirror.library.extension.makeGone
import com.example.dmerjimirror.library.extension.makeVisible
import com.example.dmerjimirror.library.model.response.Component
import com.example.dmerjimirror.library.utils.Metrics
import com.example.dmerjimirror.ui.main.view_model.UserResponseViewModel
import com.google.android.material.card.MaterialCardView
import com.google.android.material.transition.MaterialFadeThrough

class OverviewFragment : Fragment() {

    private var _binding: FragmentOverviewBinding? = null
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var overviewViewModel: OverviewViewModel
    private val userResponseViewModel: UserResponseViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        overviewViewModel =
            ViewModelProvider(this)[OverviewViewModel::class.java]

        _binding = FragmentOverviewBinding.inflate(inflater, container, false)
        val root: View = binding.root

        enterTransition = MaterialFadeThrough()
        exitTransition = MaterialFadeThrough()

        (activity as MainActivity?)?.let {
            it.supportActionBar?.title = getString(
                R.string.overview_welcome,
                userResponseViewModel.userResponse.value?.user?.fullname
            )
        }

        mRecyclerView = binding.componentsRecycler
        mRecyclerView.itemAnimator = DefaultItemAnimator()
        mRecyclerView.layoutManager = GridLayoutManager(this.context, 3)
        mRecyclerView.adapter = SmallComponentAdapter(
            requireContext(),
            arrayListOf()
        )

        userResponseViewModel.userResponse.value?.user?.let {
            overviewViewModel.refreshComponents(it.id)
        }

        binding.newsComponent.componentName.text = context?.getString(R.string.component_news_feed)
        binding.newsComponent.componentImage.setImageDrawable(
            AppCompatResources.getDrawable(
                requireContext(),
                R.drawable.news_feed
            )
        )

        overviewViewModel.components.observe(viewLifecycleOwner, Observer {
            (mRecyclerView.adapter as SmallComponentAdapter).setComponents(it)
        })

        overviewViewModel.newsFeed.observe(viewLifecycleOwner, Observer {
            if (it == null) {
                binding.newsComponent.root.cardElevation = 0f
                binding.newsComponent.componentName.text = ""
                binding.newsComponent.componentImage.setImageDrawable(null)
            } else {
                binding.newsComponent.root.cardElevation =
                    Metrics.getPxFromDpValue(5f, requireContext()).toFloat()
                binding.newsComponent.componentName.text = getString(R.string.component_news_feed)
                binding.newsComponent.componentImage.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.news_feed
                    )
                )
            }
        })

        overviewViewModel.isRefreshing.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.contentView.makeGone()
                binding.progress.makeVisible()
            } else {
                binding.contentView.makeVisible()
                binding.progress.makeGone()
            }
        })

        setupDragAndDrop()

        return root
    }

    private fun setupDragAndDrop() {
        // drag and drop
        val simpleCallback: ItemTouchHelper.SimpleCallback =
            object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.START or ItemTouchHelper.END,
                0
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    val from = viewHolder.adapterPosition
                    val to = target.adapterPosition
                    Log.d(
                        "ComponentMoved",
                        "from: ${Component.getPositionStringFromIndex(from)} to " +
                                Component.getPositionStringFromIndex(to)
                    )
                    Component.move(
                        (mRecyclerView.adapter as SmallComponentAdapter?)?.getComponents(),
                        from,
                        to
                    )
                    mRecyclerView.adapter?.notifyItemMoved(from, to)
                    return false
                }


                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}
                override fun onSelectedChanged(
                    viewHolder: RecyclerView.ViewHolder?,
                    actionState: Int
                ) {
                    super.onSelectedChanged(viewHolder, actionState)
                    if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                        val card = (viewHolder?.itemView as MaterialCardView?)
                        card?.isDragged = true
                    }
                }

                override fun clearView(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder
                ) {
                    super.clearView(recyclerView, viewHolder)
                    val card = (viewHolder.itemView as MaterialCardView?)
                    card?.isDragged = false
                    // update DB
                    val newComponents =
                        (mRecyclerView.adapter as SmallComponentAdapter?)?.getComponents()
                            ?: arrayListOf()
                    UserController.updateComponents(newComponents) {
                        (activity as? MainActivity)?.let { activity ->
                            activity.mSocket?.emit("chat message", (userResponseViewModel.userResponse.value?.user?.id ?: -1).toString())
                        }
                    }
                }

            }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(mRecyclerView)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}