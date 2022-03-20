package com.developer.takenote.featurenote.domain.usecase

import com.developer.takenote.featurenote.domain.model.Note
import com.developer.takenote.featurenote.domain.repository.NoteRepository
import com.developer.takenote.featurenote.domain.util.NoteOrder
import com.developer.takenote.featurenote.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotesUseCase(
    private val repository: NoteRepository
) {

    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.Date(orderType = OrderType.Descending)
    ): Flow<List<Note>> {
        return repository.getNotes().map { notes ->
            when(noteOrder.orderType) {
                is OrderType.Ascending -> {
                    when(noteOrder) {
                        is NoteOrder.Title-> notes.sortedBy { it.title.lowercase() }
                        is NoteOrder.Date-> notes.sortedBy { it.timeStamp }
                        is NoteOrder.Color-> notes.sortedBy { it.color }
                    }
                }
                is OrderType.Descending -> {
                    when(noteOrder) {
                        is NoteOrder.Title -> notes.sortedByDescending { it.title }
                        is NoteOrder.Date -> notes.sortedByDescending { it.timeStamp }
                        is NoteOrder.Color -> notes.sortedByDescending { it.color }
                    }
                }
            }
        }
    }

}