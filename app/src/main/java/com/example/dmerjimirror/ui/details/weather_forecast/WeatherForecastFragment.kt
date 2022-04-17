package com.example.dmerjimirror.ui.details.weather_forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.dmerjimirror.MainActivity
import com.example.dmerjimirror.R
import com.example.dmerjimirror.databinding.FragmentWeatherBinding
import com.example.dmerjimirror.databinding.FragmentWeatherForecastBinding
import com.example.dmerjimirror.library.extension.makeGone
import com.example.dmerjimirror.ui.details.weather.WeatherViewModel
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.MaterialSharedAxis

class WeatherForecastFragment : Fragment() {
    private var _binding: FragmentWeatherForecastBinding? = null
    private lateinit var weatherForecastViewModel: WeatherForecastViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        weatherForecastViewModel =
            ViewModelProvider(this)[WeatherForecastViewModel::class.java]
        (activity as MainActivity?)?.navView?.makeGone(activity)

        _binding = FragmentWeatherForecastBinding.inflate(inflater, container, false)
        val root: View = binding.root

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Y, false)

        weatherForecastViewModel.weather.observe(viewLifecycleOwner, Observer {
            binding.weatherLayout.componentHeader.componentName.text = it.name
            binding.weatherLayout.componentHeader.componentEnabledSwitch.isChecked = it.active
            binding.weatherLayout.componentHeader.componentImage.setImageDrawable(
                AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.forecast
                )
            )
            binding.weatherLayout.location.editText?.setText(it.location)
            binding.numberOfDays.text = it.numberOfDays.toString()
            binding.enableColorSwitch.isChecked = it.colored
        })


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}