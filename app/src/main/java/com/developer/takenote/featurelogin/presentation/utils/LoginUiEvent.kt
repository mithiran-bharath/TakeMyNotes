package com.developer.takenote.featurelogin.presentation.utils

sealed class LoginUiEvent {
    data class ShowSnackBar(val message: String): LoginUiEvent()
    object NavigateToForgotPassword: LoginUiEvent()
    object NavigateToUserSignUp: LoginUiEvent()
    object NavigateToHome: LoginUiEvent()
}