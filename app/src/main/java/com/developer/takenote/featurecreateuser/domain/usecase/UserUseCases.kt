package com.developer.takenote.featurecreateuser.domain.usecase

data class UserUseCases(
    val insertUserUseCase: InsertUserUseCase,
    val getAllUsersUseCase: GetAllUsersUseCase,
    val getLastInsertedUserUseCase: GetLastInsertedUserUseCase,
    val getUserByIdUseCase: GetUserByIdUseCase,
    val permanentDeleteUserAccountUseCase: PermanentDeleteUserAccountUseCase
)
