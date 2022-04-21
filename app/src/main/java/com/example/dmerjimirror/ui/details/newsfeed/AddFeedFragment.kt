package com.example.dmerjimirror.ui.details.newsfeed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.example.dmerjimirror.R
import com.example.dmerjimirror.databinding.FragmentAddFeedBinding
import com.example.dmerjimirror.dialog.RoundedBottomSheetDialogFragment
import com.example.dmerjimirror.library.model.response.Feed
import com.example.dmerjimirror.library.utils.MaterialTextInput
import com.example.dmerjimirror.listener.FeedListener
import com.google.android.material.bottomsheet.BottomSheetBehavior

class AddFeedFragment : RoundedBottomSheetDialogFragment() {

    private var _binding: FragmentAddFeedBinding? = null
    private var elementListener: FeedListener? = null
    override var state: Int = BottomSheetBehavior.STATE_COLLAPSED

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        elementListener = arguments?.getSerializable("listener") as FeedListener?
        setUpViews(view)
    }


    private fun setUpViews(view: View) {
        MaterialTextInput.setupClearErrors(view)

        binding.header.headerTitle.text = getString(R.string.news_feed_add_title)
        binding.header.cancelButton.setOnClickListener {
            dismissAllowingStateLoss()
        }
        binding.header.saveButton.text = getString(R.string.global_add)

        val items = listOf("Daily", "Monthly", "Yearly", "All")
        val adapter = ArrayAdapter(requireContext(), R.layout.drop_down_list_item, items)
        (binding.providerList.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        binding.header.saveButton.setOnClickListener {
            val name = binding.providerList.editText?.text?.toString() ?: ""
            var error = false
            if (name == "") {
                binding.providerList.error = getString(R.string.error_field_not_empty)
                error = true
            }
            if (!error) {
                elementListener?.addFeed(Feed(0, name, ""))
                dismissAllowingStateLoss()
            }

        }

    }


    companion object {

        fun newInstance(
            elementListener: FeedListener
        ): AddFeedFragment {
            val fragment = AddFeedFragment()
            val args = Bundle()
            args.putSerializable("listener", elementListener)
            fragment.arguments = args
            return fragment
        }
    }

}