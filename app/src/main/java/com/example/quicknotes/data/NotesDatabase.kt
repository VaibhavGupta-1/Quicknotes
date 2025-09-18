package com.example.quicknotes.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Note::class], // List of tables in the database
    version = 1              // Increment this if you change the schema
)
abstract class NotesDatabase : RoomDatabase() {
    abstract val dao: NoteDao // Provides the DAO to the rest of the app
}