package com.example.dmerjimirror.ui.overview

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dmerjimirror.MainActivity
import com.example.dmerjimirror.R
import com.example.dmerjimirror.adapater.SmallComponentAdapter
import com.example.dmerjimirror.databinding.FragmentOverviewBinding
import com.example.dmerjimirror.ui.components.ComponentsViewModel
import com.google.android.material.transition.MaterialFadeThrough
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

        (activity as MainActivity?)?.supportActionBar?.title =
            getString(R.string.overview_welcome, "Hey")

        mRecyclerView = binding.componentsRecycler
        mRecyclerView.itemAnimator = DefaultItemAnimator()
        mRecyclerView.layoutManager = GridLayoutManager(this.context, 3)
        mRecyclerView.adapter = SmallComponentAdapter(overviewViewModel.components.value ?: arrayListOf())

        overviewViewModel.components.observe(viewLifecycleOwner, Observer {
            mRecyclerView.adapter
        })


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}