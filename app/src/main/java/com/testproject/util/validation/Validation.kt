package com.testproject.util.validation

import android.os.Build
import androidx.annotation.RequiresApi

interface ValidationInterface {
    fun isPasswordValid(password: String) : Boolean
    fun isNameValid(name: String) : Boolean
    fun isValidToken(token:String?):Boolean
}

class Validation :ValidationInterface
{
    // name is not empty and length should be equal or greater than 3
    @RequiresApi(Build.VERSION_CODES.N)
    override fun isNameValid(name: String): Boolean {
        return  if (name.trim().isEmpty())  false
        else name.trim().chars().anyMatch(Character::isLetter) && name.trim().length>=3
    }

    override fun isValidToken(token: String?): Boolean {
        return  token?.run {
            if (token.isEmpty()|| token.contains( " ")) return false
            else true
        }?: kotlin.run {
            false
        }
    }
    override fun isPasswordValid(password: String) =  password.trim().length >= 3

}



