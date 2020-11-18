package com.testproject.data.network.api_call.network_handler

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.testproject.app.AppController

interface NetworkAvailableInterface {
    fun isInternetIsAvailable():Boolean
}
class NetworkAvailable : NetworkAvailableInterface
{
    override  fun isInternetIsAvailable(): Boolean
    {
        val connectivityManager =
            AppController.applicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        connectivityManager.activeNetwork.also {
            it?.let {
                val networkCapabilities = connectivityManager.getNetworkCapabilities(it)
                if (networkCapabilities!=null && (networkCapabilities.hasTransport(
                        NetworkCapabilities.TRANSPORT_WIFI
                    ) || networkCapabilities.hasTransport(
                        NetworkCapabilities.TRANSPORT_CELLULAR))
                )
                    return true

            }?:run{ return false }
        }
        return false
    }
}