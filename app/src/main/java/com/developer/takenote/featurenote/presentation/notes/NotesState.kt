package com.developer.takenote.featurenote.presentation.notes

import com.developer.takenote.featurenote.domain.model.Note
import com.developer.takenote.featurenote.domain.util.NoteOrder
import com.developer.takenote.featurenote.domain.util.OrderType

data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean=false
)
