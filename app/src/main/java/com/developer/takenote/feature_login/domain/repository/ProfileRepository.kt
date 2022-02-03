package com.developer.takenote.feature_login.domain.repository

import com.developer.takenote.feature_login.domain.model.ProfileData
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    fun getProfiles(): Flow<List<ProfileData>>


    suspend fun insertProfile(profileData: ProfileData)

    suspend fun deleteProfile(profileData: ProfileData)

    suspend fun getProfileById(id: String): ProfileData?


}