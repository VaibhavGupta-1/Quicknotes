package com.example.quicknotes

import android.app.Application
import androidx.room.Room
import com.example.quicknotes.data.NotesDatabase

// A simple dependency container
object AppContainer {
    lateinit var database: NotesDatabase
}

class QuickNotesApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Build the database when the app starts
        AppContainer.database = Room.databaseBuilder(
            applicationContext,
            NotesDatabase::class.java,
            "notes_db"
        ).build()
    }
}