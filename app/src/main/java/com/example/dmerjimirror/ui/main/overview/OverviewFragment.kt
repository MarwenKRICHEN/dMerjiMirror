package com.example.dmerjimirror.ui.main.overview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
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
import com.example.dmerjimirror.library.model.response.Component
import com.google.android.material.card.MaterialCardView
import com.google.android.material.transition.MaterialFadeThrough

class OverviewFragment : Fragment() {

    private var _binding: FragmentOverviewBinding? = null
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var overviewViewModel: OverviewViewModel

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

        (activity as MainActivity?)?.supportActionBar?.title = getString(R.string.overview_welcome, "Hey")

        mRecyclerView = binding.componentsRecycler
        mRecyclerView.itemAnimator = DefaultItemAnimator()
        mRecyclerView.layoutManager = GridLayoutManager(this.context, 3)
        mRecyclerView.adapter = SmallComponentAdapter(
            requireContext(),
            overviewViewModel.components.value ?: arrayListOf()
        )

        binding.newsComponent.componentName.text = context?.getString(R.string.component_news_feed)
        binding.newsComponent.componentImage.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.news_feed))

        overviewViewModel.components.observe(viewLifecycleOwner, Observer {
            mRecyclerView.adapter
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
                    val arr = (mRecyclerView.adapter as SmallComponentAdapter?)?.getComponents()
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