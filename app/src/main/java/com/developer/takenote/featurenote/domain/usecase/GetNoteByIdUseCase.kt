package com.developer.takenote.featurenote.domain.usecase

import com.developer.takenote.featurenote.domain.model.Note
import com.developer.takenote.featurenote.domain.repository.NoteRepository

class GetNoteByIdUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(id:Int): Note? {
        return repository.getNoteById(id = id)
    }
}