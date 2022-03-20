package com.developer.takenote.featureprofile.domain.repository

import com.developer.takenote.featureprofile.domain.model.ProfileData
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    fun getProfiles(): Flow<List<ProfileData>>

    suspend fun insertProfile(profileData: ProfileData)

    suspend fun deleteProfile(profileData: ProfileData)

    suspend fun getProfileById(id: String): ProfileData?

}