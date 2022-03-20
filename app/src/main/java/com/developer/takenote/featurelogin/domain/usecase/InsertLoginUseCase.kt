package com.developer.takenote.featurelogin.domain.usecase

import com.developer.takenote.featurecreateuser.domain.repository.UserRepository
import com.developer.takenote.featurecreateuser.domain.util.RegistrationUtils
import com.developer.takenote.featurelogin.domain.model.InvalidLoginException
import com.developer.takenote.featurelogin.domain.model.UserLogin
import com.developer.takenote.featurelogin.domain.repository.LoginRepository
import com.developer.takenote.ui.SharedPreferenceRepository

class InsertLoginUseCase(
    private val loginRepository: LoginRepository,
    private val userRepository: UserRepository,
    private val sharedPreferenceRepository: SharedPreferenceRepository
) {

    @Throws(InvalidLoginException::class)
    suspend operator fun invoke(userLogin: UserLogin, password: String) {

        if (RegistrationUtils.isValidUserEmail(userLogin.mailID)) {
            throw InvalidLoginException("Mail cannot be Empty")
        }

        if (password.isBlank()) {
            throw InvalidLoginException("Password cannot be Empty")
        }

        if (userLogin.checkInTime == 0L) {
            throw InvalidLoginException("CheckIn Time cannot be empty")
        }

        if (loginRepository.getLoggedUser(userLogin.mailID).isNotEmpty()) {
            throw InvalidLoginException("User is already logged")
        }

        val user = userRepository.getUserById(userLogin.mailID)
        if (user != null) {
            user.let {
                if (it.password != password) {
                    throw InvalidLoginException("Enter valid password")
                }
                loginRepository.insertLoginUser(userLogin = userLogin)
                sharedPreferenceRepository.setEmailId(userLogin.mailID)
            }
        } else {
            throw InvalidLoginException("User not found")
        }

    }

}