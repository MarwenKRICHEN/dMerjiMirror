package com.example.dmerjimirror.ui.main.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dmerjimirror.MainActivity
import com.example.dmerjimirror.adapater.LargeComponentAdapter
import com.example.dmerjimirror.databinding.FragmentComponentsBinding
import com.example.dmerjimirror.library.extension.makeVisible
import com.example.dmerjimirror.library.model.response.Component
import com.example.dmerjimirror.library.utils.DirectionAdapter
import com.example.dmerjimirror.listener.RecyclerItemNavigation
import com.google.android.material.transition.MaterialFadeThrough

class ComponentsFragment : Fragment(), RecyclerItemNavigation {

    private var _binding: FragmentComponentsBinding? = null
    private lateinit var mRecyclerView: RecyclerView

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val componentsViewModel =
            ViewModelProvider(this)[ComponentsViewModel::class.java]
        (activity as MainActivity?)?.navView?.makeVisible()
        _binding = FragmentComponentsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        enterTransition = MaterialFadeThrough()
        exitTransition = MaterialFadeThrough()

        mRecyclerView = binding.componentsRecycler
        mRecyclerView.itemAnimator = DefaultItemAnimator()
        mRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        mRecyclerView.adapter = LargeComponentAdapter(
            requireContext(),
            arrayListOf(),
            this
        )

        componentsViewModel.components.observe(viewLifecycleOwner, Observer {
            (mRecyclerView.adapter as LargeComponentAdapter?)?.setComponents(it)
        })
        return root
    }

    override fun onItemClick(component: Component) {
        DirectionAdapter(component).navigate(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}