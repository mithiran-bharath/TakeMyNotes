package com.developer.takenote.featurenote.data.repository

import com.developer.takenote.featurenote.data.datasource.NoteDao
import com.developer.takenote.featurenote.domain.model.Note
import com.developer.takenote.featurenote.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val dao: NoteDao
):NoteRepository
{
    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNotesById(id = id)
    }

    override suspend fun insertNote(note: Note) {
        return dao.insertNote(note = note)
    }

    override suspend fun deleteNote(note: Note) {
        return dao.deleteNote(note = note  )
    }

    override fun getNoteByMail(mailId: String): Flow<List<Note>> {
        return dao.getNotesFromMail(mailId)
    }
}