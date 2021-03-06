package com.example.dmerjimirror.ui.details.newsfeed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dmerjimirror.MainActivity
import com.example.dmerjimirror.R
import com.example.dmerjimirror.adapater.NewsFeedComponentAdapter
import com.example.dmerjimirror.databinding.FragmentComponentRecyclerDetailBinding
import com.example.dmerjimirror.library.controller.NewsFeedController
import com.example.dmerjimirror.library.extension.makeGone
import com.example.dmerjimirror.library.model.response.Feed
import com.example.dmerjimirror.library.model.response.NewsFeed
import com.example.dmerjimirror.library.utils.SwipeToDeleteHelper
import com.example.dmerjimirror.listener.FeedListener
import com.example.dmerjimirror.ui.details.DetailFragment
import com.example.dmerjimirror.ui.details.newsfeed.model.FeedItem
import com.example.dmerjimirror.ui.details.todo.model.ComponentHeader
import com.example.dmerjimirror.ui.details.todo.model.Items
import com.example.dmerjimirror.ui.details.todo.model.TodoAddHeader
import com.google.android.material.transition.MaterialSharedAxis

class NewsFeedDetailFragment() : DetailFragment(), View.OnClickListener, FeedListener {
    private var _binding: FragmentComponentRecyclerDetailBinding? = null
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var newsFeedViewModel: NewsFeedViewModel


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        newsFeedViewModel =
            ViewModelProvider(this)[NewsFeedViewModel::class.java]

        _binding = FragmentComponentRecyclerDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root

        (activity as MainActivity?)?.navView?.makeGone(activity)

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Y, false)


        mRecyclerView = binding.recyclerView
        mRecyclerView.itemAnimator = DefaultItemAnimator()
        mRecyclerView.layoutManager = LinearLayoutManager(this.context)

        val newsFeed = newsFeedViewModel.component.value

        mRecyclerView.adapter = NewsFeedComponentAdapter(
            requireContext(),
            requireActivity(),
            arrayListOf(
                ComponentHeader(newsFeed ?: NewsFeed()),
                TodoAddHeader(),
            ),
            this
        )

        reloadAdapter(newsFeed)

        setupSwipeGesture()

        userResponseViewModel.userResponse.value?.let {
            newsFeedViewModel.refresh(it.user.id)
        }

        newsFeedViewModel.component.observe(viewLifecycleOwner, Observer {
            if (it == null && newsFeedViewModel.isRefreshing.value == false)
                showSnackbar(binding.root)
            reloadAdapter(it)
        })

        newsFeedViewModel.isRefreshing.observe(viewLifecycleOwner, Observer {
            toggleProgressViews(it, binding.recyclerView, binding.progress)
        })



        return root
    }

    private fun reloadAdapter(newsFeed: NewsFeed?) {
        val feedList = newsFeed?.list
        val items = ArrayList<Items>()
        items.add(ComponentHeader(newsFeed ?: NewsFeed()))
        items.add(TodoAddHeader())
        for (feed in feedList ?: ArrayList()) {
            items.add(FeedItem(feed))
        }
        (mRecyclerView.adapter as? NewsFeedComponentAdapter)?.reloadItems(items)
    }

    // show add TodoElement
    override fun onClick(p0: View?) {
        activity?.supportFragmentManager?.let {
            AddFeedFragment.newInstance(this).apply {
                show(it, tag)
            }
        }
    }

    override fun addFeed(feed: Feed) {
        (mRecyclerView.adapter as NewsFeedComponentAdapter?)?.addFeedItem(FeedItem(feed))
    }

    private fun setupSwipeGesture() {
        val simpleCallback = SwipeToDeleteHelper(requireContext()) { position ->
            (mRecyclerView.adapter as NewsFeedComponentAdapter?)?.deleteItem(
                position,
                R.string.feed_deleted
            ) {
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(mRecyclerView)
    }

    override fun saveData() {
        newsFeedViewModel.component.value?.let {
            (mRecyclerView.adapter as? NewsFeedComponentAdapter)?.let { adapter ->
                val feeds = adapter.getFeeds()
                NewsFeedController.update(
                    NewsFeed(
                        it.id,
                        it.name,
                        it.position,
                        adapter.getIsEnabled(),
                        it.userid,
                        adapter.getShowDescription(),
                        feeds
                    )
                ) { _, _ ->
                    dataSaved()
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}