package com.developer.takenote.feature_login.domain.use_case.profile_use_cases

import com.developer.takenote.feature_login.domain.model.ProfileData
import com.developer.takenote.feature_login.domain.repository.ProfileRepository
import com.developer.takenote.feature_login.domain.util.RegistrationUtils
import java.lang.Exception

class InsertProfileUseCase(private val repository: ProfileRepository) {

    suspend operator fun invoke(profileData: ProfileData) {
        if(!RegistrationUtils.isValidUserEmail(profileData.mailId)) {
            throw Exception("")
        }
        repository.insertProfile(profileData = profileData)
    }
}