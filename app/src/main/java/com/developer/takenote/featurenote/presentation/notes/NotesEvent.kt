package com.developer.takenote.featurenote.presentation.notes

import com.developer.takenote.featurenote.domain.model.Note
import com.developer.takenote.featurenote.domain.util.NoteOrder

sealed class NotesEvent{
    data class Order(val noteOrder: NoteOrder): NotesEvent()
    data class DeleteNote(val note: Note):NotesEvent()
    object RestoreNote: NotesEvent()
    object ToggleOrderSection: NotesEvent()
}
