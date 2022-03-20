package com.developer.takenote.featurenote.data.datasource

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class DataBaseMigrationHelper {


    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                "ALTER TABLE note ADD COLUMN emailId TEXT"
            )
        }
    }


    /**
     * Query issue is found in this migration
     */
    val MIGRATIN_2_3 = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {

            database.execSQL(
                "UPDATE note SET emailId =" +
                        " (SELECT mailID FROM loginInfo WHERE loginStatus = 1 AND emailId = mailID)"
            )


        }
    }

    val MIGRATION_1_4 = object : Migration(1,4) {
        override fun migrate(database: SupportSQLiteDatabase) {

            database.execSQL(
                "ALTER TABLE note ADD COLUMN emailId TEXT"
            )

            database.execSQL(
                "UPDATE note SET emailId =" +
                        " (SELECT mailID FROM loginInfo WHERE loginStatus = 1 AND emailId = mailID)"
            )
        }
    }

    val MIGRATIN_3_4 = object : Migration(3, 4) {
        override fun migrate(database: SupportSQLiteDatabase) {

            database.execSQL(
                "UPDATE note SET emailId =" +
                        " (SELECT mailID FROM loginInfo WHERE loginStatus = 1)"
            )


        }
    }

}