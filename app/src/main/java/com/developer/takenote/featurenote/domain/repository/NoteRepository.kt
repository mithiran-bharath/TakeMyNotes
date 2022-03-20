package com.developer.takenote.featurenote.domain.repository

import com.developer.takenote.featurenote.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getNotes() : Flow<List<Note>>

    suspend fun getNoteById(id: Int): Note?

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note:Note)

    fun getNoteByMail(mailId: String): Flow<List<Note>>

}