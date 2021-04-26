package io.techmeskills.an02onl_plannerapp.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import io.techmeskills.an02onl_plannerapp.models.Note
import io.techmeskills.an02onl_plannerapp.models.User
import kotlinx.coroutines.flow.Flow

@Dao
abstract class NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveNote(note: Note): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveNotes(notes: List<Note>): List<Long>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract fun updateNote(note: Note)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract fun updateNotes(notes: List<Note>)

    @Delete
    abstract fun deleteNote(note: Note)

    @Delete
    abstract fun deleteNotes(notes: List<Note>)

    @Query("SELECT * FROM notes ORDER BY id DESC")
    abstract fun getAllNotes(): List<Note>

    @Query("SELECT * FROM notes ORDER BY id DESC")
    abstract fun getAllNotesLiveFlow(): Flow<List<Note>>

    @Query("SELECT * FROM notes ORDER BY id DESC")
    abstract fun getAllNotesLiveData(): LiveData<List<Note>>

    @Query("SELECT * FROM notes WHERE userId == :userId ORDER BY id DESC")
    abstract fun getAllNotesByUserId(userId: Long): List<Note>

    @Query("SELECT * FROM notes where userId==:userId ORDER BY id DESC")
    abstract fun getCurrentNotesLiveFlow(userId: Long): Flow<List<Note>>

    @Query("UPDATE notes SET fromCloud = 1")
    abstract fun setAllNotesSyncWithCloud()
}