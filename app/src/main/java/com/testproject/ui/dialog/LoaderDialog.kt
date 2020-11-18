package com.testproject.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.MutableLiveData
import com.testproject.R
import com.testproject.util.AppUtils

interface LoaderDialogInterface
{
    fun showLoader()
    fun hideLoader()
}

class LoaderDialog : DialogFragment() , LoaderDialogInterface
{
    var data = MutableLiveData<Boolean>()

    override fun onStart()
    {
        super.onStart()
        val windows: Window = dialog!!.window!!
        val wlp: WindowManager.LayoutParams = windows.attributes
        windows.attributes = wlp
    }

    override fun show(manager: FragmentManager, tag: String?) {
        val ft: FragmentTransaction
        try {
            ft = manager.beginTransaction()
            ft.add(this, tag)
             ft.commit()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View {
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val view: View = inflater.inflate(R.layout.dialog, container)
        data.observe(
            this,
            { aBoolean: Boolean -> if (aBoolean) dismiss() }
        )
        this.isCancelable = false
        return view
    }


    override  fun showLoader()
        {
            try {
                AppUtils.getVisibleActivity()?.let { activity->
                    val fragmentManager =activity.supportFragmentManager
                    val loader =
                        fragmentManager.findFragmentByTag("loader") as LoaderDialog?
                    if (loader != null) return
                    LoaderDialog().show(fragmentManager, "loader")
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    override fun hideLoader()
        {
            try {
                AppUtils.getVisibleActivity()?.let { activity ->
                    val fragmentManager =activity.supportFragmentManager
                    val loader: LoaderDialog
                    val fragment  = fragmentManager.findFragmentByTag("loader")
                    if(fragment!=null)
                    {
                        loader= fragment as LoaderDialog
                        loader.data.value = true
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
}