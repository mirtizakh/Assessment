package com.testproject.data.network.interceptor



import com.testproject.util.preference.PreferenceInterface
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterceptor(var preferenceInterface: PreferenceInterface) : Interceptor
{
    override fun intercept(chain: Interceptor.Chain): Response
    {
          val originalRequest = chain.request()
        val request = originalRequest.newBuilder()
            .addHeader("Authorization",if(preferenceInterface.isLoggedIn)"Bearer "+ preferenceInterface.token else "")
            .build()
          return chain.proceed(request)
    }
}