package com.developer.takenote.featurecreateuser.domain.usecase

import com.developer.takenote.featurecreateuser.domain.model.UserAccount
import com.developer.takenote.featurecreateuser.domain.repository.UserRepository

class GetUserByIdUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(id: String): UserAccount? {
        return userRepository.getUserById(id)
    }

}