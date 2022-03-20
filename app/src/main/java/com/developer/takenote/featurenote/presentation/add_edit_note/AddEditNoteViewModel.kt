package com.developer.takenote.featurenote.presentation.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developer.takenote.featurenote.domain.model.InValidNoteException
import com.developer.takenote.featurenote.domain.model.Note
import com.developer.takenote.featurenote.domain.usecase.NoteUseCases
import com.developer.takenote.ui.SharedPreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle,
    sharedPreferenceRepository: SharedPreferenceRepository
):ViewModel() {

    private val _noteTitle = mutableStateOf(NoteTextFieldState(
        hint = "Enter title"
    ))
    val noteTitle: State<NoteTextFieldState> = _noteTitle

    private val _noteContent = mutableStateOf(NoteTextFieldState(
        hint = "Enter some content"
    ))
    val noteContent: State<NoteTextFieldState> = _noteContent

    private val _noteColor = mutableStateOf(Note.notesColor.random().toArgb())
    val noteColor: State<Int> = _noteColor

    private val _email = mutableStateOf(String())
    val email: State<String> = _email

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null


    init {
        savedStateHandle.get<Int>("noteId")?.let{ noteId ->
            if(noteId != -1) {
                viewModelScope.launch {
                    noteUseCases.getNoteById(noteId)?.also { note ->
                        currentNoteId = note.id
                        _noteTitle.value = noteTitle.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        _noteContent.value = noteContent.value.copy(
                            text = note.content,
                            isHintVisible = false
                        )

                        _noteColor.value = noteColor.value
                    }
                }
            }
        }
        savedStateHandle.get<Int>("noteColor")?.let {
            _noteColor.value = it
        }
        savedStateHandle.get<String>("emailId")?.let {
            _email.value = it
        }
    }
    fun onEvent(event: AddEditNoteEvent) {
        when(event) {
            is AddEditNoteEvent.EnteredTitle -> {
                _noteTitle.value = noteTitle.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.EnteredContent -> {
                _noteContent.value = noteContent.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.ChangeTitleFocus -> {
                _noteTitle.value = noteTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            noteTitle.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.ChangeContentFocus -> {
                _noteContent.value = noteContent.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            noteContent.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.ChangeColor -> {
                _noteColor.value = event.color
            }
            is AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        noteUseCases.insertNote(
                            Note(
                                title = noteTitle.value.text,
                                content = noteContent.value.text,
                                timeStamp = System.currentTimeMillis(),
                                color = noteColor.value,
                                emailId =email.value,
                                id = currentNoteId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                    } catch (exception: InValidNoteException) {
                        _eventFlow.emit(UiEvent.ShowSnackBar(
                            message = exception.message ?: "Couldn't save notes"
                        ))
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackBar(val message: String): UiEvent()
        object SaveNote:UiEvent()
    }
}


