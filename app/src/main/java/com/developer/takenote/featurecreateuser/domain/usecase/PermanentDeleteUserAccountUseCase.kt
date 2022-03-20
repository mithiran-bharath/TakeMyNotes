package com.developer.takenote.featurecreateuser.domain.usecase

import com.developer.takenote.featurecreateuser.domain.model.UserAccount
import com.developer.takenote.featurecreateuser.domain.repository.UserRepository

class PermanentDeleteUserAccountUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(userAccount: UserAccount) {
        userRepository.deleteUser(userAccount)
    }

}