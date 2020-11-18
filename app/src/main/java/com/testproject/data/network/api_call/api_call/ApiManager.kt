package com.testproject.data.network.api_call.api_call

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.testproject.data.network.response.response_models.ItemsResponse
import com.testproject.data.network.response.response_models.LoginResponse
import com.testproject.util.AppUtils
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit


interface ApiManager
{
    // This api will call on  (DetailsFragment , DetailsViewModel)
      @POST("app/item-list")
      suspend  fun items() : Response<ItemsResponse>

    // This api will call  on (LoginFragment,LoginViewModel)
    @POST("app/authenticate")
    suspend   fun login(@Body  body: JsonObject) : Response<LoginResponse>

    companion object
   {
      operator  fun invoke(interceptor: Interceptor)  : ApiManager
      {
          val gson = GsonBuilder().create()

          val okHttpClient: OkHttpClient = OkHttpClient.Builder()
                  .addNetworkInterceptor(interceptor)
                  .callTimeout(20, TimeUnit.SECONDS)
                  .readTimeout(20, TimeUnit.SECONDS)
                  .connectTimeout(20, TimeUnit.SECONDS).build()

         return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(AppUtils.URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiManager::class.java)
      }
   }
}