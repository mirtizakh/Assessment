package com.testproject.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.testproject.util.preference.PreferenceInterface
@Suppress("UNCHECKED_CAST")
class SplashViewModelFactory(
    private var preferenceInterface: PreferenceInterface
) : ViewModelProvider.NewInstanceFactory()
{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T
    {
        return SplashViewModel(preferenceInterface) as T
    }
}