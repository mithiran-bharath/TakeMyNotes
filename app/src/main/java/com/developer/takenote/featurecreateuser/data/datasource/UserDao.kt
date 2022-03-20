package com.developer.takenote.featurecreateuser.data.datasource

import androidx.room.*
import com.developer.takenote.featurecreateuser.domain.model.UserAccount
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(userAccount: UserAccount)

    @Delete
    suspend fun deleteUser(userAccount: UserAccount)

    @Query("SELECT * FROM userAccount WHERE mailID = :mail")
    suspend fun getUserAccount(mail:String): UserAccount?

    @Query("SELECT * FROM userAccount")
    fun getUsers(): Flow<List<UserAccount>>

    @Query("SELECT * FROM userAccount ORDER BY  createdDate DESC LIMIT 1")
    fun getLastInsertedUserAccount(): Flow<UserAccount>

    @Query("SELECT COUNT(mailID) FROM userAccount WHERE mailID = :mailId")
    suspend fun isUserExists(mailId: String): Int

    @Query("select Count(*) from userAccount where mailID = :mailId and password = :password")
    suspend fun verifyPassword(mailId: String, password: String): Int

}