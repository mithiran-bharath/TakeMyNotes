package com.developer.takenote.featurecreateuser.presentation.signup

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developer.takenote.featurecreateuser.domain.model.AccountStatus
import com.developer.takenote.featurecreateuser.domain.model.InvalidUserException
import com.developer.takenote.featurecreateuser.domain.model.UserAccount
import com.developer.takenote.featurecreateuser.domain.usecase.UserUseCases
import com.developer.takenote.featurecreateuser.presentation.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userUseCases: UserUseCases
) : ViewModel() {

    private val _mailId = mutableStateOf("")
    val mailId: State<String> = _mailId

    private val _passWord = mutableStateOf("")
    val password: State<String> = _passWord

    private val _userSecurityHelperState = mutableStateOf(UserSecurityHelperState())
    val userSecurityHelperStateState: State<UserSecurityHelperState> = _userSecurityHelperState


    private val _signUpEventFlow = MutableSharedFlow<UiEvent>()
    val signUpEventFlow = _signUpEventFlow.asSharedFlow()


    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.EnteredEmail -> {
                _mailId.value = event.value
            }
            is SignUpEvent.EnteredPassword -> {
                _passWord.value = event.value
            }
            is SignUpEvent.SignUpUser -> {
                viewModelScope.launch {
                    try {

                        val currentDate = System.currentTimeMillis()
                        userUseCases.insertUserUseCase(
                            UserAccount(
                                mailID = mailId.value,
                                userName = "${mailId.value}_$currentDate",
                                password = password.value,
                                createdDate = currentDate,
                                modifiedDate = currentDate,
                                accountStatus = AccountStatus.ACTIVE.ordinal,
                                isSecurityEnabled = !userSecurityHelperStateState.value.isHelperCheckBoxClicked,
                                securityQues = userSecurityHelperStateState.value.helperQuestion,
                                securityAns = userSecurityHelperStateState.value.helperQuestionAns
                            )
                        )
                        _signUpEventFlow.emit(UiEvent.SaveNewUser)
                    } catch (exception: InvalidUserException) {
                        _signUpEventFlow.emit(
                            UiEvent.ShowSnackBar(
                                message = exception.message ?: "Couldn't save new user"
                            )
                        )
                    }
                }
            }
            is SignUpEvent.CheckBoxValueChanged -> {

                if (event.isSecurityEnabled) {
                    _userSecurityHelperState.value =
                        userSecurityHelperStateState.value.resetQuestionState()
                }
                _userSecurityHelperState.value = userSecurityHelperStateState.value.copy(
                    isHelperCheckBoxClicked = event.isSecurityEnabled
                )

            }
            is SignUpEvent.SpinnerValueChanged -> {
                _userSecurityHelperState.value = userSecurityHelperStateState.value.copy(
                    isSpinnerExpanded = event.isExpanded
                )
            }
            is SignUpEvent.QuestionValueChanged -> {
                _userSecurityHelperState.value = userSecurityHelperStateState.value.copy(
                    helperQuestion = event.question
                )
            }
            is SignUpEvent.QuestionAnsValueChanged -> {
                _userSecurityHelperState.value = userSecurityHelperStateState.value.copy(
                    helperQuestionAns = event.answer
                )
            }
            is SignUpEvent.LoginUser -> {
                viewModelScope.launch {
                    _signUpEventFlow.emit(UiEvent.LoginEvent)
                }
            }

        }
    }
}