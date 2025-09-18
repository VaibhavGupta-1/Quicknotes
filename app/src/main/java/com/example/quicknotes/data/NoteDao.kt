package com.example.quicknotes.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao // Marks this as a Data Access Object
interface NoteDao {

    // Inserts a new note. If a note with the same ID exists, it's replaced.
    @Upsert
    suspend fun upsertNote(note: Note)

    // Deletes a note
    @Delete
    suspend fun deleteNote(note: Note)

    // Gets all notes from the table, ordered by the most recent timestamp
    @Query("SELECT * FROM notes ORDER BY timestamp DESC")
    fun getAllNotes(): Flow<List<Note>>
}