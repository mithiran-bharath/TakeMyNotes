package com.developer.takenote.featurenote.data.datasource

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.developer.takenote.featurecreateuser.data.datasource.UserDao
import com.developer.takenote.featurecreateuser.domain.model.UserAccount
import com.developer.takenote.featurelogin.data.datasource.LoginDao
import com.developer.takenote.featurelogin.domain.model.UserLogin
import com.developer.takenote.featurenote.domain.model.Note


@Database(
    entities = [Note::class, UserAccount::class, UserLogin::class],
    version = 4,
    exportSchema = false
)
abstract class NoteDatabase : RoomDatabase() {

    abstract val noteDao: NoteDao
    abstract val userDao: UserDao
    abstract val loginDao: LoginDao

    companion object {
        const val DATABASE_NAME = "notes_db"
    }

}