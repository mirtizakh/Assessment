package com.testproject.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.testproject.util.preference.PreferenceInterface
import com.testproject.util.validation.ValidationInterface

@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory(private var validationInterface: ValidationInterface, private var apiInterface: LoginApiInterface, private var preferenceInterface: PreferenceInterface) : ViewModelProvider.NewInstanceFactory()
{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T
    {
        return LoginViewModel(validationInterface,apiInterface,preferenceInterface) as T
    }
}