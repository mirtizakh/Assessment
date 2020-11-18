package com.testproject.ui.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.testproject.activity.MainActivity
import com.testproject.R
import com.testproject.app.AppController
import com.testproject.databinding.LoginFragmentBinding
import com.testproject.util.AppUtils
import org.kodein.di.generic.instance

class LoginFragment : Fragment()
{
    private val loginViewModelFactory : LoginViewModelFactory by AppController.kodein().instance()

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val binding  :LoginFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.login_fragment,container,false)
        viewModel = ViewModelProvider(this, loginViewModelFactory).get(LoginViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?)
    {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        // On success response of login redirecting user to view item screen.
        viewModel.liveDataResponseSuccess.observe(viewLifecycleOwner, { result->
            result?.let {
                if(activity!=null && activity is MainActivity)
                (activity as MainActivity).navigate(R.id.action_loginFragment_to_viewItemFragment)

                viewModel.liveDataResponseSuccess.value=null }
        })

        // On error 401 , 400, 404 , 500 and timeout
        viewModel.liveDataResponseError.observe(viewLifecycleOwner, { result->
            result?.let {
                AppUtils.showSnackBar(result)
                viewModel.liveDataResponseError.value=null }
        })

        // On network not found showing error
        // If required we can redirect user to an internet error screen
        viewModel.liveDataResponseNetworkError.observe(viewLifecycleOwner, { result->
            result?.let {
                AppUtils.showSnackBar(result)
                viewModel.liveDataResponseNetworkError.value=null }
        })
    }

}