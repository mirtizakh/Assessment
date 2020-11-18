package com.testproject.ui.view_item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.testproject.R
import com.testproject.activity.MainActivity
import com.testproject.app.AppController
import com.testproject.databinding.ViewItemFragmentBinding
import org.kodein.di.generic.instance

class ViewItemFragment : Fragment() {
    private lateinit var viewModel: ViewItemViewModel
    private val viewItemViewModelFactory : ViewItemViewModelFactory by AppController.kodein().instance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val binding  : ViewItemFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.view_item_fragment,container,false)
        viewModel = ViewModelProvider(this, viewItemViewModelFactory).get(ViewItemViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?)
    {
        super.onActivityCreated(savedInstanceState)
        // On success response of login redirecting user to view item screen.
        viewModel.liveDataScreenRedirection.observe(viewLifecycleOwner, { result ->
            result?.let {
                if (activity != null && activity is MainActivity)
                    (activity as MainActivity).navigate(R.id.action_viewItemFragment_to_detailsFragment)

                viewModel.liveDataScreenRedirection.value = null }
        })
    }
}