package com.developer.takenote.feature_login.domain.util

import android.util.Patterns
import java.util.regex.Pattern

object RegistrationUtils {

    fun isValidUserEmail(email:String):Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidUserName(userName: String): Boolean {
        return userName.isNotEmpty() && userName.length == USERNAME_LENGTH
    }

    fun isValidPassword(password: String): Boolean {
        return Pattern.matches(PASSWORD_MATCHERS,password)
    }

}