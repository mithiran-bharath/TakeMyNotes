package com.developer.takenote.feature_login.domain.repository

interface LoginRepository {

    suspend fun getUser()

    suspend fun insertUser()

}