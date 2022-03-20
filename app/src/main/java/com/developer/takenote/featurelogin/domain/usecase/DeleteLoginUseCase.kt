package com.developer.takenote.featurelogin.domain.usecase


import com.developer.takenote.featurelogin.domain.repository.LoginRepository

class DeleteLoginUseCase(private val loginRepository: LoginRepository) {
    suspend operator fun invoke(mailID: String) {
        loginRepository.deleteLoginInfo(mailID = mailID)
    }
}