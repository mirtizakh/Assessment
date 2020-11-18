package com.testproject.ui.splash

import androidx.lifecycle.ViewModel
import com.testproject.R
import com.testproject.util.preference.PreferenceInterface

class SplashViewModel (private var preferenceInterface: PreferenceInterface) : ViewModel()
{
   fun checkLoginStatusAndGetScreen() = if(preferenceInterface.isLoggedIn) R.id.action_splashFragment_to_viewItemFragment
                                  else R.id.action_splashFragment_to_loginFragment
}