package com.developer.takenote.featurelogin.presentation.login

sealed class UserLoginEvent {

    data class EnteredMail(val email: String): UserLoginEvent()
    data class EnteredPassword(val password: String): UserLoginEvent()
    object UserLogin: UserLoginEvent()
    object ForgotPassword: UserLoginEvent()
    object SignUp: UserLoginEvent()

}