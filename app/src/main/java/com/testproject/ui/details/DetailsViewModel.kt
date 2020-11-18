package com.testproject.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testproject.data.network.api_call.ResponseStatus
import com.testproject.data.network.response.ResponseMessages
import com.testproject.data.network.response.response_models.Item
import kotlinx.coroutines.launch
import com.testproject.data.network.response.response_models.ErrorResponse


class DetailsViewModel(private var apiInterface: DetailsApiInterface) : ViewModel() {

    var liveDataResponseSuccess: MutableLiveData<List<Item>> = MutableLiveData()
    var liveDataResponseError: MutableLiveData<String> = MutableLiveData()
    var liveDataResponseNetworkError: MutableLiveData<String> = MutableLiveData()

    // Getting all items from server
    fun getItems()
    {
        viewModelScope.launch {
            val authResponse = apiInterface.items()

            when(authResponse.status)
            {
                ResponseStatus.SUCCESS->
                {
                    if(authResponse.responseData!=null  && authResponse.responseData.itemList.size>0)
                    {
                        liveDataResponseSuccess.value = authResponse.responseData.itemList
                    }
                    else ResponseMessages.SOME_THING_WENT_WRONG.message
                }
                // We can perform difference activities on these status if required
                ResponseStatus.AUTH_ERROR , ResponseStatus.ERROR, ResponseStatus.VALIDATION_ERROR, ResponseStatus.NOT_FOUND_ERROR->
                {
                    if(authResponse.responseData is ErrorResponse)
                    {
                        val message = (authResponse.responseData as ErrorResponse).message?: ResponseMessages.SOME_THING_WENT_WRONG.message
                        liveDataResponseError.value = message
                    }
                    else liveDataResponseError.value = ResponseMessages.SOME_THING_WENT_WRONG.message
                }
                ResponseStatus.NETWORK_ERROR->   liveDataResponseNetworkError.value = ResponseMessages.NETWORK_ERROR.message
            }
        }
    }
}