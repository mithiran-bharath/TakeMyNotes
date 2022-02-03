package com.developer.takenote.feature_login.data.repository

import com.developer.takenote.feature_login.data.data_source.ProfileDao
import com.developer.takenote.feature_login.domain.model.ProfileData
import com.developer.takenote.feature_login.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow

class ProfileRepositoryImpl(private val profileDao: ProfileDao) : ProfileRepository{

    override fun getProfiles(): Flow<List<ProfileData>> {
        return profileDao.getProfiles()
    }

    override suspend fun insertProfile(profileData: ProfileData) {
        return profileDao.insertProfile(profileData)
    }

    override suspend fun deleteProfile(profileData: ProfileData) {
        return profileDao.deleteProfile(profileData)
    }

    override suspend fun getProfileById(id: String): ProfileData? {
       return profileDao.getProfileById(id)
    }


}