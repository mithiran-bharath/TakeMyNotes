package com.developer.takenote.featurelogin.data.datasource

import androidx.room.*
import com.developer.takenote.featurelogin.domain.model.UserLogin
import kotlinx.coroutines.flow.Flow

@Dao
interface LoginDao {

    @Query("SELECT * FROM loginInfo")
    fun observeLogins(): Flow<List<UserLogin>>

    @Query("SELECT * FROM loginInfo")
    suspend fun getLogins(): List<UserLogin>

    @Query("SELECT mailID FROM loginInfo WHERE loginStatus = 1 AND mailID = :mailID")
    suspend fun loggedUserList(mailID: String): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLoginUser(userLogin: UserLogin)

    @Query("SELECT * FROM loginInfo WHERE mailID = :mailID")
    fun loginHistory(mailID: String): Flow<List<UserLogin>>

    @Query("DELETE FROM loginInfo WHERE mailID = :mailID")
    suspend fun deleteLoginInfo(mailID: String)

    @Query("UPDATE loginInfo SET loginStatus = 0, checkOutTime = :checkOutTime WHERE mailID = :mailID")
    suspend fun logout(mailID: String, checkOutTime: Long)

}