package com.developer.takenote.featurecreateuser.domain.util

import android.util.Patterns
import androidx.core.util.PatternsCompat
import java.util.regex.Pattern

object RegistrationUtils {

    fun isValidUserEmail(email:String):Boolean {
        return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidUserName(userName: String): Boolean {
        return userName.isNotEmpty() && userName.length == USERNAME_LENGTH
    }

    fun isValidPassword(password: String): Boolean {
        return Pattern.matches(PASSWORD_MATCHERS,password)
    }

}