package com.developer.takenote.featurelogin.domain.repository

import com.developer.takenote.featurelogin.domain.model.UserLogin
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    fun observeLogins() : Flow<List<UserLogin>>

    suspend fun getLogins() : List<UserLogin>

    suspend fun getLoggedUser(mailID: String): List<String>

    suspend fun insertLoginUser(userLogin: UserLogin)

    fun loginHistory(mailId:String): Flow<List<UserLogin>>

    suspend fun deleteLoginInfo(mailID: String)

    suspend fun logout(mailID: String, checkOutTime: Long)
}