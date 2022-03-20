package com.developer.takenote.featurelogin.data.repository

import com.developer.takenote.featurelogin.data.datasource.LoginDao
import com.developer.takenote.featurelogin.domain.model.UserLogin
import com.developer.takenote.featurelogin.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow


class LoginRepositoryImpl(private val loginDao: LoginDao) : LoginRepository {
    override fun observeLogins(): Flow<List<UserLogin>> {
        return loginDao.observeLogins()
    }

    override suspend fun getLogins(): List<UserLogin> {
        return loginDao.getLogins()
    }

    override suspend fun getLoggedUser(mailID: String): List<String> {
        return loginDao.loggedUserList(mailID = mailID)
    }

    override suspend fun insertLoginUser(userLogin: UserLogin) {
        loginDao.insertLoginUser(userLogin)
    }

    override fun loginHistory(mailId: String): Flow<List<UserLogin>> {
        return loginDao.loginHistory(mailId)
    }

    override suspend fun deleteLoginInfo(mailID: String) {
        return loginDao.deleteLoginInfo(mailID = mailID)
    }

    override suspend fun logout(mailID: String, checkOutTime: Long) {
        loginDao.logout(mailID = mailID, checkOutTime = checkOutTime)
    }
}