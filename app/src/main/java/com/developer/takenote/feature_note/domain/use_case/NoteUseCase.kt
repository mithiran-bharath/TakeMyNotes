package com.developer.takenote.feature_note.domain.use_case

data class NoteUseCase(
    val getNotes: GetNotesUseCase,
    val delete: DeleteNoteUseCase,
    val insertNote: InsertNoteUseCase,
    val getNoteById: GetNoteByIdUseCase
)