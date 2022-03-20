package com.developer.takenote.featurecreateuser.domain.usecase

import com.developer.takenote.featurecreateuser.domain.model.UserAccount
import com.developer.takenote.featurecreateuser.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetLastInsertedUserUseCase(private val userRepository: UserRepository) {
    operator fun invoke():Flow<UserAccount> {
        return userRepository.getLastInsertedUserAccount()
    }
}