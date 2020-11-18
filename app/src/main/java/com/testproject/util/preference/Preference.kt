package com.testproject.util.preference

import android.content.Context
import android.content.SharedPreferences
const val PREF_NAME = "NYE"
const val PRIVATE_MODE = 0

enum class PreferenceKeys(val value: String) {
    PREF_TOKEN("Token"),
    PREF_USER_NAME("USERNAME"),
    PREF_IS_LOGIN("IsLogin")
}

interface PreferenceInterface
{
    var  isLoggedIn:Boolean
    var  token :String
    var  userName :String
}

class Preference(context: Context):PreferenceInterface
{
      private var pref: SharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)

    override var  isLoggedIn: Boolean
        get() = pref.getBoolean(PreferenceKeys.PREF_IS_LOGIN.value,false)
        set(value) { pref.edit().putBoolean(PreferenceKeys.PREF_IS_LOGIN.value,value).apply() }

    override var  token: String
        get() = pref.getString(PreferenceKeys.PREF_TOKEN.value,"").toString()
        set(value) { pref.edit().putString(PreferenceKeys.PREF_TOKEN.value,value).apply() }

    override var  userName: String
        get() = pref.getString(PreferenceKeys.PREF_USER_NAME.value,"").toString()
        set(value) { pref.edit().putString(PreferenceKeys.PREF_USER_NAME.value,value).apply() }

}