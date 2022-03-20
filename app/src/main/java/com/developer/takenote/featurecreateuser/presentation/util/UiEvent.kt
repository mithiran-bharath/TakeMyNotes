package com.developer.takenote.featurecreateuser.presentation.util

import android.app.UiAutomation

sealed class UiEvent {
    data class ShowSnackBar(val message: String): UiEvent()
    object SaveNewUser:UiEvent()
    object LoginEvent: UiEvent()
}
