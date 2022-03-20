package com.developer.takenote.featurecreateuser.presentation.signup



sealed class SignUpEvent {
    data class EnteredEmail(val value: String): SignUpEvent()
    data class EnteredPassword(val value: String): SignUpEvent()
    data class CheckBoxValueChanged(val isSecurityEnabled: Boolean): SignUpEvent()
    data class SpinnerValueChanged(val isExpanded: Boolean): SignUpEvent()
    data class QuestionValueChanged(val question: String): SignUpEvent()
    data class QuestionAnsValueChanged(val answer: String): SignUpEvent()
    object SignUpUser: SignUpEvent()
    object LoginUser: SignUpEvent()
}