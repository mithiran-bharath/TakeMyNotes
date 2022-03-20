package com.developer.takenote.featurelogin.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "loginInfo")
data class UserLogin(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val mailID: String,
    val checkInTime: Long,
    val checkOutTime: Long? = 0,
    val loginStatus: Boolean
) {

}

class InvalidLoginException(message: String) : Exception(message)
