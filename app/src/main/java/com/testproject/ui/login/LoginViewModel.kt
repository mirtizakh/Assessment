package com.testproject.ui.login

import android.text.Editable
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testproject.data.network.api_call.ResponseStatus
import com.testproject.data.network.response.ResponseMessages
import com.testproject.util.DataUtil
import com.testproject.util.enums.TextFieldState
import kotlinx.coroutines.launch
import com.testproject.data.network.response.response_models.ErrorResponse
import com.testproject.util.preference.PreferenceInterface
import com.testproject.util.validation.ValidationInterface

class LoginViewModel (private var validationInterface: ValidationInterface, private var apiInterface: LoginApiInterface, private var preferenceInterface: PreferenceInterface): ViewModel() {

    var liveDataUserName: MutableLiveData<String> = MutableLiveData()
    var liveDataPassword: MutableLiveData<String> = MutableLiveData()

    // Live data for email text field status
    private var liveDataUserNameFieldStatus: MutableLiveData<TextFieldState> = MutableLiveData()
    // Live data for password text field status
    private var liveDataPasswordFieldStatus: MutableLiveData<TextFieldState> = MutableLiveData()

    var liveDataButtonEnable: MutableLiveData<Boolean> = MutableLiveData()

    var liveDataResponseSuccess: MutableLiveData<Boolean> = MutableLiveData()
    var liveDataResponseError: MutableLiveData<String> = MutableLiveData()
    var liveDataResponseNetworkError: MutableLiveData<String> = MutableLiveData()

    fun onTextChangedUserNameField(s: Editable?) {
        validateUserName()
        enableButton()
    }
    fun onTextChangedPasswordField(s: Editable?) {
       validatePassword()
        enableButton()
    }

    fun onButtonClick(view: View) {
        if(validateTextFields())
         login()
        else liveDataResponseError.value = ResponseMessages.SOME_THING_WENT_WRONG.message
    }

    // Checking email field validation
    private fun validateUserName() {
        if ((liveDataUserName.value ?: "").isEmpty()) {
            liveDataUserNameFieldStatus.value = TextFieldState.IDLE
        } else { liveDataUserNameFieldStatus.value = if (validationInterface.isNameValid(liveDataUserName.value ?: "")
        ) TextFieldState.SUCCESS else TextFieldState.ERROR
        }
    }
    // Checking password field validation
    private fun validatePassword() {
        if ((liveDataPassword.value ?: "").isEmpty()) {
            liveDataPasswordFieldStatus.value = TextFieldState.IDLE
        } else { liveDataPasswordFieldStatus.value =  if (validationInterface.isPasswordValid(liveDataPassword.value ?: ""))
            TextFieldState.SUCCESS else TextFieldState.ERROR
        }
    }

    // enable login button
    private fun enableButton()
    {
        liveDataButtonEnable.value = validateTextFields()
    }

    private fun validateTextFields() = liveDataPasswordFieldStatus.value == TextFieldState.SUCCESS && liveDataUserNameFieldStatus.value == TextFieldState.SUCCESS


    private fun login()
    {
        viewModelScope.launch {
            val authResponse = apiInterface.login(DataUtil.getLoginJson(liveDataUserName.value ?: "",
                liveDataPassword.value?:""))

            when(authResponse.status)
            {
                ResponseStatus.SUCCESS->
                {
                    if(authResponse.responseData!=null  &&
                       validationInterface.isValidToken(authResponse.responseData.token))
                    {
                        preferenceInterface.isLoggedIn = true
                        preferenceInterface.userName = liveDataUserName.value?:""
                        preferenceInterface.token = authResponse.responseData.token?:""
                        liveDataResponseSuccess.value = true
                    }
                    else ResponseMessages.SOME_THING_WENT_WRONG.message
                }
                // We can perform difference activities on these status if required
                ResponseStatus.AUTH_ERROR , ResponseStatus.ERROR, ResponseStatus.VALIDATION_ERROR,ResponseStatus.NOT_FOUND_ERROR->
                {
                    if(authResponse.responseData is ErrorResponse)
                    {
                        val message = (authResponse.responseData as ErrorResponse).message?:ResponseMessages.SOME_THING_WENT_WRONG.message
                        liveDataResponseError.value = message
                    }
                    else liveDataResponseError.value = ResponseMessages.SOME_THING_WENT_WRONG.message
                }
                ResponseStatus.NETWORK_ERROR->   liveDataResponseNetworkError.value = ResponseMessages.NETWORK_ERROR.message
            }
        }
    }

}