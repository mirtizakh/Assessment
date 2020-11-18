package com.testproject.ui.login

import com.google.gson.JsonObject
import com.testproject.data.network.api_call.Resource
import com.testproject.data.network.response.response_models.LoginResponse

interface LoginApiInterface
{
    suspend  fun  login(body: JsonObject): Resource<LoginResponse>
}