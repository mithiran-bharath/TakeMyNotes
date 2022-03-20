package com.developer.takenote.featurecreateuser.domain.repository

import com.developer.takenote.featurecreateuser.domain.model.UserAccount
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUserAccounts(): Flow<List<UserAccount>>

    fun getLastInsertedUserAccount(): Flow<UserAccount>

    suspend fun getUserById(id: String): UserAccount?

    suspend fun insertUser(userAccount: UserAccount)

    suspend fun deleteUser(userAccount: UserAccount)

    suspend fun isUserExists(mailID: String): Int

    suspend fun verifyUser(mailID: String,password: String): Int

}