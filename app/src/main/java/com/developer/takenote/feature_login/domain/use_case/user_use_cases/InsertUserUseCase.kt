package com.developer.takenote.feature_login.domain.use_case.user_use_cases

import com.developer.takenote.feature_login.domain.model.UserAccount
import com.developer.takenote.feature_login.domain.repository.UserRepository
import com.developer.takenote.feature_login.domain.use_case.profile_use_cases.InsertProfileUseCase
import com.developer.takenote.feature_login.domain.util.RegistrationUtils
import java.lang.Exception

class InsertUserUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(userAccount: UserAccount) {
        if(!RegistrationUtils.isValidUserName(userAccount.userName)) {
             throw Exception("")
        }
        if(RegistrationUtils.isValidPassword(userAccount.password)) {
            throw Exception("")
        }
        userRepository.insertUser(userAccount)

    }
}