package com.testproject.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.testproject.R

class MainActivity : AppCompatActivity()
{
    private var  navController: NavController? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
    }

    // This function is used to do navigation between screens
    fun navigate(destinationId: Int, bundle: Bundle? = null)
    {
        val liveData = MutableLiveData<Boolean>()
        liveData.value = true
        liveData.observe(this, {
            if (it != null && it) {
                try {
                    if (navController != null)
                    {
                        if (bundle != null) navController?.navigate(
                            destinationId, bundle)
                        else navController?.navigate(destinationId)
                    }
                    liveData.value = null
                } catch (e: Exception) {
                    e.printStackTrace() }
            }
        })
    }
}