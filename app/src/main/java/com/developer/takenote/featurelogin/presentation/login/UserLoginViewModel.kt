package com.developer.takenote.featurelogin.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.developer.takenote.featurecreateuser.domain.usecase.UserUseCases
import com.developer.takenote.featurelogin.domain.model.InvalidLoginException
import com.developer.takenote.featurelogin.domain.model.UserLogin
import com.developer.takenote.featurelogin.domain.usecase.UserLoginUseCases
import com.developer.takenote.featurelogin.presentation.utils.LoginUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserLoginViewModel @Inject constructor(
    private val userLoginUseCases: UserLoginUseCases
) : ViewModel() {

    private val _mailID = mutableStateOf("")
    val mailID: State<String> = _mailID

    private val _password = mutableStateOf("")
    val password: State<String> = _password

    private val _userLoginEventFlow = MutableSharedFlow<LoginUiEvent>()
    val userLoginEventFlow = _userLoginEventFlow.asSharedFlow()

    fun onEvent(event: UserLoginEvent) {
        when (event) {
            is UserLoginEvent.EnteredMail -> {
                _mailID.value = event.email
            }
            is UserLoginEvent.EnteredPassword -> {
                _password.value = event.password
            }
            is UserLoginEvent.UserLogin -> {
                viewModelScope.launch {
                    try {

                        val currentDate = System.currentTimeMillis()
                        userLoginUseCases.insertLoginUseCase(
                            UserLogin(
                                mailID = mailID.value,
                                checkInTime = currentDate,
                                loginStatus = true
                            ),
                            password = password.value
                        )
                        _userLoginEventFlow.emit(LoginUiEvent.NavigateToHome)
                    } catch (exception: InvalidLoginException) {
                        _userLoginEventFlow.emit(
                            LoginUiEvent.ShowSnackBar(
                                exception.message ?: "Couldn't login user"
                            )
                        )
                    }
                }

            }
            is UserLoginEvent.ForgotPassword -> {
                viewModelScope.launch {
                    _userLoginEventFlow.emit(LoginUiEvent.NavigateToForgotPassword)
                }
            }
            is UserLoginEvent.SignUp -> {
                viewModelScope.launch {
                    _userLoginEventFlow.emit(LoginUiEvent.NavigateToUserSignUp)
                }
            }
        }
    }

}