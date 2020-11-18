package com.testproject.ui.splash

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.testproject.activity.MainActivity
import com.testproject.R
import com.testproject.app.AppController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.kodein.di.generic.instance

class SplashFragment : Fragment()
{
    private lateinit var viewModel: SplashViewModel
    private val splashViewModelFactory : SplashViewModelFactory by AppController.kodein().instance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.splash_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this,splashViewModelFactory).get(SplashViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?)
    {
        super.onActivityCreated(savedInstanceState)
        redirectToNextScreen()
    }

    private fun redirectToNextScreen()
    {
        lifecycleScope.launch {
            delay(3000L)
            if(activity!=null && activity is MainActivity)
            (activity as MainActivity).navigate(viewModel.checkLoginStatusAndGetScreen())
        }
    }

}