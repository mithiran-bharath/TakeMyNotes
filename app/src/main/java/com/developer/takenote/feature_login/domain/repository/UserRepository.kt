package com.developer.takenote.feature_login.domain.repository

import com.developer.takenote.feature_login.domain.model.UserAccount
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUserAccounts() : Flow<List<UserAccount>>

    suspend fun getUserById(id:String): UserAccount?

    suspend fun insertUser(userAccount: UserAccount)

    suspend fun deleteUser(userAccount: UserAccount)

}