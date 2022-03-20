package com.developer.takenote.featureprofile.data.datasource

import androidx.room.*
import com.developer.takenote.featureprofile.domain.model.ProfileData
import kotlinx.coroutines.flow.Flow


@Dao
interface ProfileDao {

    @Query("SELECT * FROM profileData")
    fun getProfiles(): Flow<List<ProfileData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profileData: ProfileData)

    @Delete
    suspend fun deleteProfile(deleteProfileData: ProfileData)

    @Query("SELECT * FROM profileData WHERE mailId = :id")
    suspend fun getProfileById(id: String): ProfileData?
}