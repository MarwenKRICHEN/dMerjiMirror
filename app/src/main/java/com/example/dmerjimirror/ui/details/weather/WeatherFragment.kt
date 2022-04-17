package com.example.dmerjimirror.ui.details.weather

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
import com.example.dmerjimirror.databinding.FragmentWeatherBinding
import com.example.dmerjimirror.library.extension.makeGone
import com.example.dmerjimirror.library.model.Component
import com.example.dmerjimirror.ui.main.overview.OverviewViewModel
import com.google.android.material.card.MaterialCardView
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.MaterialSharedAxis

class WeatherFragment: Fragment() {
    private var _binding: FragmentWeatherBinding? = null
    private lateinit var weatherViewModel: WeatherViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        weatherViewModel =
            ViewModelProvider(this)[WeatherViewModel::class.java]
        (activity as MainActivity?)?.navView?.makeGone(activity)

        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        val root: View = binding.root

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Y, false)

        weatherViewModel.weather.observe(viewLifecycleOwner, Observer {
            binding.weatherLayout.componentHeader.componentName.text = it.name
            binding.weatherLayout.componentHeader.componentEnabledSwitch.isChecked = it.active
            binding.weatherLayout.componentHeader.componentImage.setImageDrawable(
                AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.weather
                )
            )
            binding.weatherLayout.location.editText?.setText(it.location)
        })


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}