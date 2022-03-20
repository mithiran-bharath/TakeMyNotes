package com.developer.takenote.featurecreateuser.domain.usecase

import com.developer.takenote.featurecreateuser.domain.model.InvalidUserException
import com.developer.takenote.featurecreateuser.domain.model.UserAccount
import com.developer.takenote.featurecreateuser.domain.repository.UserRepository
import com.developer.takenote.featurecreateuser.domain.util.RegistrationUtils
import com.developer.takenote.utils.isNotZero
import kotlin.Exception

class InsertUserUseCase(private val userRepository: UserRepository) {


    @Throws(InvalidUserException::class, Exception::class)
    suspend operator fun invoke(userAccount: UserAccount) {
        if (!RegistrationUtils.isValidUserEmail(userAccount.mailID)) {
            throw InvalidUserException("Enter the valid mail Id")
        }

        if (!RegistrationUtils.isValidPassword(userAccount.password)) {
            throw InvalidUserException("Enter the valid password")
        }

        if(!userAccount.isSecurityVerified()) {
            throw InvalidUserException("Security error occurs")
        }

        if(userRepository.isUserExists(userAccount.mailID).isNotZero()) {
            throw InvalidUserException("User Already Existed")
        }

        try {
            userRepository.insertUser(userAccount = userAccount)
        } catch (exception: Exception) {
            throw exception.message?.let { InvalidUserException(it) }!!
        }

    }
}