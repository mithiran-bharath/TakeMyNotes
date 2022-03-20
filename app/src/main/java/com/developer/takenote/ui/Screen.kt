package com.developer.takenote.ui

sealed class Screen(val route: String) {
    object SignUpScreen: Screen("sign_up_screen")
    object SignInScreen: Screen("sign_in_screen")
    object NoteNotesScreen: Screen("note_screen")
    object AddEditNoteNotesScreen: Screen("add_edit_note_screen")
    object ForgotPasswordScreen: Screen("forgot_password")
}