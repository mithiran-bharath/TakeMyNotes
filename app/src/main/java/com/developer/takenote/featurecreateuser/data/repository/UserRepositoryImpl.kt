package com.developer.takenote.featurecreateuser.data.repository

import com.developer.takenote.featurecreateuser.data.datasource.UserDao
import com.developer.takenote.featurecreateuser.domain.model.UserAccount
import com.developer.takenote.featurecreateuser.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(private val userDao: UserDao) : UserRepository {
    override fun getUserAccounts(): Flow<List<UserAccount>> {
        return userDao.getUsers()
    }

    override fun getLastInsertedUserAccount(): Flow<UserAccount> {
        return userDao.getLastInsertedUserAccount()
    }

    override suspend fun getUserById(id: String): UserAccount? {
        return userDao.getUserAccount(id)
    }

    override suspend fun insertUser(userAccount: UserAccount) {
        return userDao.insertUser(userAccount)
    }

    override suspend fun isUserExists(mailID: String): Int {
        return userDao.isUserExists(mailId = mailID)
    }

    override suspend fun deleteUser(userAccount: UserAccount) {
        return userDao.deleteUser(userAccount)
    }

    override suspend fun verifyUser(mailID: String, password: String): Int {
        return userDao.verifyPassword(mailId = mailID, password = password)
    }
}