package com.developer.takenote.feature_login.data.data_source

import androidx.room.*
import com.developer.takenote.feature_login.domain.model.UserAccount
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userAccount: UserAccount)

    @Delete
    suspend fun deleteUser(userAccount: UserAccount)

    @Query("SELECT * FROM userAccount WHERE mailID = :mail")
    suspend fun getUserAccount(mail:String): UserAccount?

    @Query("SELECT * FROM userAccount")
    fun getUsers(): Flow<List<UserAccount>>

}