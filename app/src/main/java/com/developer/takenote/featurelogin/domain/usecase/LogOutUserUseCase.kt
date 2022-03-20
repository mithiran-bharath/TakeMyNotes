package com.developer.takenote.featurelogin.domain.usecase

import com.developer.takenote.featurelogin.domain.repository.LoginRepository

class LogOutUserUseCase(
    private val loginRepository: LoginRepository
) {

    suspend operator fun invoke(mailID: String, checkOutTime: Long) {
        loginRepository.logout(mailID,checkOutTime)
    }

}