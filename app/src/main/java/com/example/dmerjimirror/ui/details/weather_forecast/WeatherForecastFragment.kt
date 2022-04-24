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
import com.example.dmerjimirror.ui.details.DetailFragment
import com.example.dmerjimirror.ui.details.weather.WeatherViewModel
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.MaterialSharedAxis

class WeatherForecastFragment : DetailFragment() {
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

        userResponseViewModel.userResponse.value?.let {
            weatherForecastViewModel.refresh(it.user.id)
        }
        var stepperNb = binding.numberOfDays.text.toString().toInt()

        weatherForecastViewModel.weather.observe(viewLifecycleOwner, Observer {
            if (it == null && weatherForecastViewModel.isRefreshing.value == false)
                showSnackbar(binding.root)
            binding.weatherLayout.componentHeader.componentName.text = it?.name ?: ""
            binding.weatherLayout.componentHeader.componentEnabledSwitch.isChecked = it?.active ?: false
            binding.weatherLayout.componentHeader.componentImage.setImageDrawable(
                AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.forecast
                )
            )
            binding.weatherLayout.location.editText?.setText(it?.location ?: "")
            binding.numberOfDays.text = (it?.numberofdays ?: 3).toString()
            stepperNb = it?.numberofdays ?: 3
            binding.enableColorSwitch.isChecked = it?.colored ?: false
        })


        weatherForecastViewModel.isRefreshing.observe(viewLifecycleOwner, Observer {
            toggleProgressViews(it, binding.contentMain, binding.progress)
        })

        binding.stepperPlus.setOnClickListener {
            if (stepperNb < 12)
                stepperNb++
            binding.numberOfDays.text = stepperNb.toString()
        }

        binding.stepperMinus.setOnClickListener {
            if (stepperNb > 1)
                stepperNb--
            binding.numberOfDays.text = stepperNb.toString()
        }


        return root
    }

    override fun saveData() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}