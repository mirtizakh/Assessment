package com.testproject.util

import com.google.gson.JsonObject

object DataUtil {

    // Login Request
    fun getLoginJson(username:String,password:String): JsonObject {
        val jsonObject = JsonObject()
        jsonObject.addProperty("username", username)
        jsonObject.addProperty("password",password)
        return jsonObject
    }

}