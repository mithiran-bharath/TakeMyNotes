package com.developer.takenote.ui

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit

import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow

import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SharedPreferenceRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {


    private val mailId = stringPreferencesKey("email_id")

    suspend fun setEmailId(email: String) {
        dataStore.edit { session ->
            session[mailId] = email
        }
    }


    fun getMailId(): Flow<String> {
        return dataStore.data.map { session ->
            session[mailId] ?: ""
        }
    }
}