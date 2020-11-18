package com.testproject.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class DetailsViewModelFactory(private var apiInterface: DetailsApiInterface) : ViewModelProvider.NewInstanceFactory()
{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T
    {
        return DetailsViewModel(apiInterface) as T
    }
}