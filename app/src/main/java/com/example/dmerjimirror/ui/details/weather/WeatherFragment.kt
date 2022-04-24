package com.example.dmerjimirror.ui.details.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.dmerjimirror.MainActivity
import com.example.dmerjimirror.R
import com.example.dmerjimirror.databinding.FragmentWeatherBinding
import com.example.dmerjimirror.library.controller.WeatherController
import com.example.dmerjimirror.library.extension.makeGone
import com.example.dmerjimirror.library.extension.makeVisible
import com.example.dmerjimirror.library.model.response.Weather
import com.example.dmerjimirror.ui.details.DetailFragment
import com.example.dmerjimirror.ui.main.view_model.UserResponseViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialSharedAxis

class WeatherFragment : DetailFragment() {
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

        userResponseViewModel.userResponse.value?.let {
            weatherViewModel.refresh(it.user.id)
        }

        weatherViewModel.weather.observe(viewLifecycleOwner, Observer {
            if (it == null && weatherViewModel.isRefreshing.value == false)
                showSnackbar(binding.root)
            binding.weatherLayout.componentHeader.componentName.text = it?.name ?: ""
            binding.weatherLayout.componentHeader.componentEnabledSwitch.isChecked =
                it?.active ?: false
            binding.weatherLayout.componentHeader.componentImage.setImageDrawable(
                AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.weather
                )
            )
            binding.weatherLayout.location.editText?.setText(it?.location ?: "")
        })


        weatherViewModel.isRefreshing.observe(viewLifecycleOwner, Observer {
            toggleProgressViews(it, binding.contentMain, binding.progress)
        })


        return root
    }

    override fun saveData(): Boolean {
        weatherViewModel.weather.value?.let {
            if (!checkFields()) {
                WeatherController.update(
                    Weather(
                        it.id,
                        it.name,
                        it.position,
                        binding.weatherLayout.componentHeader.componentEnabledSwitch.isChecked,
                        it.userid,
                        binding.weatherLayout.location.editText?.text.toString()
                    )
                ) { _, _ -> }
                return true
            }
        }
        return false
    }

    private fun checkFields(): Boolean {
        var error = false

        if ((binding.weatherLayout.location.editText?.text ?: "").toString() == "") {
            error = true
            binding.weatherLayout.location.error = getString(R.string.error_field_not_empty)
        }

        return error
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}