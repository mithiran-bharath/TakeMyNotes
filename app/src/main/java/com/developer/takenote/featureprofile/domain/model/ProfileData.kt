package com.developer.takenote.featureprofile.domain.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "profileData", indices = [Index(value = ["userName"], unique = true)])
data class ProfileData(
    val name: String? = "",
    @PrimaryKey(autoGenerate = false)
    val mailId: String,
    val userName: String? = "",
    val avatar: String? = "",
    val bannerImage: String? = "",
    val description: String? = ""
) {

}