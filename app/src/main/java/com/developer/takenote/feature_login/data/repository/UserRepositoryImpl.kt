package com.developer.takenote.feature_login.data.repository

import com.developer.takenote.feature_login.data.data_source.UserDao
import com.developer.takenote.feature_login.domain.model.UserAccount
import com.developer.takenote.feature_login.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(private val userDao: UserDao) : UserRepository {
    override fun getUserAccounts(): Flow<List<UserAccount>> {
        return userDao.getUsers()
    }

    override suspend fun getUserById(id: String): UserAccount? {
        return userDao.getUserAccount(id)
    }

    override suspend fun insertUser(userAccount: UserAccount) {
        return userDao.insertUser(userAccount)
    }

    override suspend fun deleteUser(userAccount: UserAccount) {
        return userDao.deleteUser(userAccount)
    }
}