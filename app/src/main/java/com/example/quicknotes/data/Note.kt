// In file: data/Note.kt
package com.example.quicknotes.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes") // This marks the class as a database table
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // Primary key that auto-increments
    val title: String,
    val content: String,
    val timestamp: Long // Store timestamp as a Long for easier sorting
)