
package com.testproject.util

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.testproject.R
import com.testproject.app.AppController

object AppUtils {

    const val URL = "http://94.206.102.22/"

    fun getVisibleActivity():AppCompatActivity? {
        return when {
            AppController.applicationContext().mainActivity != null -> AppController.applicationContext().mainActivity
            else -> null
        }
    }

    fun showSnackBar(msg: String?, success: Boolean=false)
    {
        val activity = getVisibleActivity()
        if(activity!=null)
        {
            hideKeyboard(activity)
            val activityView: View = activity.findViewById(R.id.content_main)
                val snackbar = Snackbar.make(activityView, msg ?: "", Snackbar.LENGTH_LONG)
                val view: View = snackbar.view
                view.setBackgroundColor(if (success) Color.parseColor("#32CD32") else Color.RED)
                snackbar.show()
        }
    }


   private fun hideKeyboard(activity: AppCompatActivity?) {
        if(activity!=null)
        {
            val view: View = activity.findViewById(R.id.content_main)
            val imm: InputMethodManager =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}