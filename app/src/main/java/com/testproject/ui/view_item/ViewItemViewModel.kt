package com.testproject.ui.view_item

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.testproject.util.preference.PreferenceInterface

class ViewItemViewModel (preferenceInterface: PreferenceInterface) : ViewModel() {

    var liveDataUserName: MutableLiveData<String> = MutableLiveData()
    var liveDataScreenRedirection: MutableLiveData<Boolean> = MutableLiveData()
    init {
        liveDataUserName.value = preferenceInterface.userName
    }

    fun onButtonClick(view: View) {
        liveDataScreenRedirection.value = true
    }
}