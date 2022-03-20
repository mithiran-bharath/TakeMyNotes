package com.developer.takenote.featureprofile.domain.usecase

import com.developer.takenote.featureprofile.domain.model.ProfileData
import com.developer.takenote.featureprofile.domain.repository.ProfileRepository
import com.developer.takenote.featurecreateuser.domain.util.RegistrationUtils
import java.lang.Exception

class InsertProfileUseCase(private val repository: ProfileRepository) {

    suspend operator fun invoke(profileData: ProfileData)  {
        if(!RegistrationUtils.isValidUserEmail(profileData.mailId)) {
            throw Exception("")
        }
        repository.insertProfile(profileData = profileData)
    }
}