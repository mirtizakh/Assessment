package com.testproject.ui.view_item

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.testproject.util.preference.PreferenceInterface

@Suppress("UNCHECKED_CAST")
class ViewItemViewModelFactory(private var preferenceInterface: PreferenceInterface) : ViewModelProvider.NewInstanceFactory()
{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T
    {
        return ViewItemViewModel(preferenceInterface) as T
    }
}