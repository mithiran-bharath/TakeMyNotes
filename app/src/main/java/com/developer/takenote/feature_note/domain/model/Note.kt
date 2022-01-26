package com.developer.takenote.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.developer.takenote.ui.theme.*

@Entity
data class Note(
    val title: String,
    val content: String,
    val timeStamp: Long,
    val color: Int,
    @PrimaryKey
    val id: Int? = null
) {
    companion object {
        val notesColor = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}

class InValidNoteException(message: String):Exception(message)