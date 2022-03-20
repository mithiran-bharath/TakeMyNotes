package com.developer.takenote.featurelogin.domain.usecase

data class UserLoginUseCases(
    val insertLoginUseCase: InsertLoginUseCase,
    val deleteLoginUseCase: DeleteLoginUseCase,
    val logOutUserUseCase: LogOutUserUseCase
)
