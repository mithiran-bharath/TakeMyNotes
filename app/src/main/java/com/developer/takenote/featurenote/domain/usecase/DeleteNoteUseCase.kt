package com.developer.takenote.featurenote.domain.usecase

import com.developer.takenote.featurenote.domain.model.Note
import com.developer.takenote.featurenote.domain.repository.NoteRepository

class DeleteNoteUseCase(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(note: Note) {
        repository.deleteNote(note = note)
    }

}