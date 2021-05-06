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

    @Delete
    abstract fun deleteNote(note: Note)

    @Delete
    abstract fun deleteNotes(notes: List<Note>)

    @Query("SELECT * FROM notes WHERE userName == :userName ORDER BY id DESC")
    abstract fun getAllNotesByUserId(userName: String): List<Note>

    @Query("SELECT * FROM notes where userName==:userName ORDER BY id DESC")
    abstract fun getCurrentNotesLiveFlow(userName: String): Flow<List<Note>>

    @Query("UPDATE notes SET fromCloud = 1")
    abstract fun setAllNotesSyncWithCloud()
}