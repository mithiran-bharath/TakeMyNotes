package com.developer.takenote.featurenote.domain.usecase

data class NoteUseCases(
    val getNotes: GetNotesUseCase,
    val delete: DeleteNoteUseCase,
    val insertNote: InsertNoteUseCase,
    val getNoteById: GetNoteByIdUseCase,
    val getNotesFromMail: GetNotesFromMailIDUseCase
)