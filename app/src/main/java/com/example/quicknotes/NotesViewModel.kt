// In file: NotesViewModel.kt
package com.example.quicknotes

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quicknotes.data.Note
import com.example.quicknotes.data.NoteDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NotesViewModel(
    private val dao: NoteDao
) : ViewModel() {

    private val _notes = dao.getAllNotes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(NoteState())

    val state = combine(_state, _notes) { state, notes ->
        state.copy(
            notes = notes
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteState())


    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    dao.deleteNote(event.note)
                }
            }
            NotesEvent.SaveNote -> {
                val title = state.value.title
                val content = state.value.content

                if (title.isBlank() || content.isBlank()) {
                    return
                }

                val note = Note(
                    title = title,
                    content = content,
                    timestamp = System.currentTimeMillis()
                )

                viewModelScope.launch {
                    dao.upsertNote(note)
                }

                _state.value = _state.value.copy(
                    title = "",
                    content = ""
                )
            }
            is NotesEvent.SetContent -> {
                _state.value = _state.value.copy(content = event.content)
            }
            is NotesEvent.SetTitle -> {
                _state.value = _state.value.copy(title = event.title)
            }
            // Logic for the new events
            is NotesEvent.BackupNotes -> {
                Log.d("NotesViewModel", "Backup event received.")
                // TODO: Call your DriveServiceHelper.uploadDatabaseFile here
                // For now, we show a Toast for confirmation
                Toast.makeText(event.context, "Backup started...", Toast.LENGTH_SHORT).show()
            }
            is NotesEvent.RestoreNotes -> {
                Log.d("NotesViewModel", "Restore event received.")
                // TODO: Call your DriveServiceHelper.downloadDatabaseFile here
                Toast.makeText(event.context, "Restore started...", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

data class NoteState(
    val notes: List<Note> = emptyList(),
    val title: String = "",
    val content: String = ""
)

// The single, correct sealed interface for all UI events
sealed interface NotesEvent {
    object SaveNote : NotesEvent
    data class SetTitle(val title: String) : NotesEvent
    data class SetContent(val content: String) : NotesEvent
    data class DeleteNote(val note: Note) : NotesEvent
    data class BackupNotes(val context: android.content.Context) : NotesEvent
    data class RestoreNotes(val context: android.content.Context) : NotesEvent
}