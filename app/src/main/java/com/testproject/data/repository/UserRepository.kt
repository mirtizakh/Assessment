package com.testproject.data.repository

import com.google.gson.JsonObject
import com.testproject.data.network.api_call.middle_ware.ResponseMiddleware
import com.testproject.ui.details.DetailsApiInterface
import com.testproject.ui.login.LoginApiInterface
import com.testproject.data.network.api_call.api_call.ApiManager


class UserRepository(
    private val responseMiddleware: ResponseMiddleware,
    private val apiManager: ApiManager) :  LoginApiInterface, DetailsApiInterface
{
    // Login Api call
    override suspend fun login(body: JsonObject)= responseMiddleware.networkCall { apiManager.login(body) }
    override suspend fun items() = responseMiddleware.networkCall { apiManager.items()}
}