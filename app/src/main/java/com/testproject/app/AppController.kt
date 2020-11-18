package com.testproject.app

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.testproject.activity.MainActivity
import com.testproject.data.network.api_call.middle_ware.ResponseMiddleware
import com.testproject.data.network.interceptor.NetworkConnectionInterceptor
import com.testproject.data.repository.UserRepository
import com.testproject.ui.details.DetailsViewModelFactory
import com.testproject.ui.login.LoginViewModelFactory
import com.testproject.ui.splash.SplashViewModelFactory
import com.testproject.ui.view_item.ViewItemViewModelFactory
import com.testproject.data.network.api_call.api_call.ApiManager
import com.testproject.data.network.api_call.network_handler.NetworkAvailable
import com.testproject.ui.dialog.LoaderDialog
import com.testproject.util.preference.Preference
import com.testproject.util.validation.Validation
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class AppController : Application() , Application.ActivityLifecycleCallbacks
{
    var mainActivity : MainActivity?=null

    init {
        instance = this
    }

    override fun onCreate()
    {
        super.onCreate()
        registerActivityLifecycleCallbacks(this)
    }
 companion object
 {
     private lateinit var instance : AppController
     private var mainActivityVisible = false
     private var mainActivityPause = false

     private val kodein = Kodein.lazy {
         bind() from singleton { Preference(instance) }
         bind() from singleton { Validation() }
         bind() from singleton { LoaderDialog() }
         bind() from singleton { NetworkConnectionInterceptor(instance()) }
         bind() from singleton { ApiManager(instance())}
         bind() from singleton { NetworkAvailable() }
         bind() from provider { SplashViewModelFactory(instance()) }
         bind() from singleton { ResponseMiddleware(instance(),instance()) }
         bind() from singleton { UserRepository(instance(), instance()) }
         bind() from singleton { LoginViewModelFactory(instance(),instance(),instance()) }
         bind() from singleton { ViewItemViewModelFactory(instance()) }
         bind() from singleton { DetailsViewModelFactory(instance()) }

     }
     fun kodein() = kodein
     fun applicationContext() : AppController = instance
     //Checking if Main activity is in foreground or is visible?
     fun isMainActivityVisible() = mainActivityVisible
     fun isMainActivityPause() = mainActivityPause
 }

    // ActivityLifecycleCallbacks
    override fun onActivityPaused(activity: Activity)
    {
        if(activity is MainActivity) mainActivityPause = true
    }

    override fun onActivityStarted(activity: Activity)
    {}
    override fun onActivityDestroyed(activity: Activity)
    {
            if(activity is MainActivity)
            {
                mainActivityPause = false
                mainActivity=null
                mainActivityVisible = false
            }
    }
    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
    override fun onActivityStopped(activity: Activity) {}
   override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?)
    {
        if(activity is MainActivity)
        {
            mainActivityVisible = true
            mainActivity=activity
            mainActivityPause = false
        }
    }
    override fun onActivityResumed(activity: Activity)
    {
        if(activity is MainActivity)
        {
            mainActivityVisible = true
            mainActivity=activity
            mainActivityPause = false
        }
    }
}