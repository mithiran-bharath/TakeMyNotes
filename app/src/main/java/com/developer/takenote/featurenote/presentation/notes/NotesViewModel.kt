package com.developer.takenote.featurenote.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developer.takenote.featurenote.domain.model.Note
import com.developer.takenote.featurenote.domain.usecase.NoteUseCases
import com.developer.takenote.featurenote.domain.util.NoteOrder
import com.developer.takenote.featurenote.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val notesUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(NotesState())
    val state: State<NotesState> = _state

    private var recentlyDeletedNote: Note? = null

    private val _emailID = mutableStateOf("")
    val email: State<String>  = _emailID

    private var getNotesJob: Job? = null
    private var getNotesFromMailJob: Job? = null

    init {

        savedStateHandle.get<String>("emailId")?.let { emailId ->
            _emailID.value = emailId
            if (emailId.isNotEmpty()) {
                getNotesByMailId(NoteOrder.Date(OrderType.Descending), emailId)
            }

        }
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.Order -> {
                if (state.value.noteOrder::class == event.noteOrder::class &&
                    state.value.noteOrder.orderType::class == event.noteOrder.orderType::class
                ) {
                    return
                }

                getNotesByMailId(event.noteOrder, email.value)
            }
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    notesUseCases.delete(event.note)
                    recentlyDeletedNote = event.note
                }
            }
            is NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    notesUseCases.insertNote(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }
            is NotesEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder) {
        getNotesJob?.cancel()

        getNotesJob = notesUseCases.getNotes(noteOrder = noteOrder)
            .onEach { notes ->
                _state.value = state.value.copy(
                    notes = notes,
                    noteOrder = noteOrder
                )
            }.launchIn(viewModelScope)
    }

    private fun getNotesByMailId(noteOrder: NoteOrder, mailId: String) {
        getNotesFromMailJob?.cancel()

        getNotesFromMailJob = notesUseCases.getNotesFromMail(noteOrder = noteOrder, mailId = mailId)
            .onEach { notes ->
                _state.value = state.value.copy(
                    notes = notes,
                    noteOrder = noteOrder
                )
            }.launchIn(viewModelScope)
    }

}