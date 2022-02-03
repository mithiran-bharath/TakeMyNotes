package com.developer.takenote.feature_login.domain.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "userAccount", indices = [Index(value = ["userName"], unique = true)])
data class UserAccount(

    @PrimaryKey(autoGenerate = false)
    val mailID: String,

    val userName : String,

    val password: String,

    val createdDate: Long,

    val modifiedDate: Long,

    val accountStatus: String,

    val isHelperNeed: Boolean = false,

    val helperQuestion: String,

    val helperAnswer: String
) {
    companion object {
        val helperQues = listOf<String>(
            "What is the name of your dog?",
            "What is your favorite color?",
            "What is your birthday?"
        )
    }
}
