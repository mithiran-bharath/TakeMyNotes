package com.developer.takenote.featurecreateuser.domain.model

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

    val accountStatus: Int = AccountStatus.ACTIVE.ordinal,

    val isSecurityEnabled: Boolean = true,

    val securityQues: String = securityQuestions[0],

    val securityAns: String
) {

    fun isSecurityVerified():Boolean {
        return if(isSecurityEnabled) {
            securityAns.isNotEmpty()
        } else {
            securityAns.isEmpty()
        }
    }

    companion object {
        val securityQuestions = listOf<String>(
            "What is the name of your dog?",
            "What is your favorite color?",
            "What is your birthday?"
        )


    }
}

class InvalidUserException(message: String):Exception(message)
enum class AccountStatus(){
    ACTIVE,
    DEACTIVATE,
    DELETE
}

