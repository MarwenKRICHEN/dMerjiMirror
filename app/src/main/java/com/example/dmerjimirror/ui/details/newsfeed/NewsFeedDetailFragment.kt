package com.example.dmerjimirror.ui.details.newsfeed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dmerjimirror.MainActivity
import com.example.dmerjimirror.R
import com.example.dmerjimirror.adapater.NewsFeedComponentAdapter
import com.example.dmerjimirror.adapater.TodoComponentAdapter
import com.example.dmerjimirror.databinding.FragmentComponentRecyclerDetailBinding
import com.example.dmerjimirror.library.extension.makeGone
import com.example.dmerjimirror.library.extension.makeVisible
import com.example.dmerjimirror.library.model.Feed
import com.example.dmerjimirror.library.model.NewsFeed
import com.example.dmerjimirror.library.model.Todo
import com.example.dmerjimirror.library.model.TodoElement
import com.example.dmerjimirror.library.utils.SwipeToDeleteHelper
import com.example.dmerjimirror.listener.FeedListener
import com.example.dmerjimirror.ui.details.newsfeed.model.FeedItem
import com.example.dmerjimirror.ui.details.todo.AddTodoElementFragment
import com.example.dmerjimirror.ui.details.todo.TodoDetailViewModel
import com.example.dmerjimirror.ui.details.todo.model.ComponentHeader
import com.example.dmerjimirror.ui.details.todo.model.TodoAddHeader
import com.example.dmerjimirror.ui.details.todo.model.TodoItem
import com.google.android.material.transition.MaterialSharedAxis

class NewsFeedDetailFragment(): Fragment(), View.OnClickListener, FeedListener {
    private var _binding: FragmentComponentRecyclerDetailBinding? = null
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var newsFeedViewModel: NewsFeedViewModel

    private var newsFeed: NewsFeed? = null
    private var feedList: ArrayList<Feed>? = null

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

        newsFeed = newsFeedViewModel.component.value
        feedList = newsFeedViewModel.feedList.value

        mRecyclerView.adapter = NewsFeedComponentAdapter(
            requireContext(),
            requireActivity(),
            arrayListOf(
                ComponentHeader(newsFeed ?: NewsFeed()),
                TodoAddHeader(),
            ),
            this
        )
        val feedItems = ArrayList<FeedItem>()
        for (feed in feedList ?: ArrayList()) {
            feedItems.add(FeedItem(feed))
        }
        (mRecyclerView.adapter as NewsFeedComponentAdapter?)?.addFeedItems(feedItems)
        setupSwipeGesture()



        return root
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
                // TODO: delete from DB
//                deleteItem((lastRemovedItem as TodoItem?)?.todo?.id)
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(mRecyclerView)
    }

    override fun onPause() {
        (activity as MainActivity?)?.navView?.makeVisible()
        super.onPause()
    }

    override fun onDestroyView() {
        (activity as MainActivity?)?.navView?.makeVisible()
        super.onDestroyView()
        _binding = null
    }

}