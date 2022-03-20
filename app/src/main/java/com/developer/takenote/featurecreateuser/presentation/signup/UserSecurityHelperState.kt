package com.developer.takenote.featurecreateuser.presentation.signup

import com.developer.takenote.featurecreateuser.domain.model.UserAccount

data class UserSecurityHelperState(
    val helperQuestion: String = UserAccount.securityQuestions[0],
    val isHelperCheckBoxClicked: Boolean = false,
    val isSpinnerExpanded: Boolean = false,
    val helperQuestionAns: String = "",
) {
    fun resetQuestionState(): UserSecurityHelperState {
        return this.copy(
            isHelperCheckBoxClicked = true,
            helperQuestion = UserAccount.securityQuestions[0],
            helperQuestionAns = ""
        )
    }
}