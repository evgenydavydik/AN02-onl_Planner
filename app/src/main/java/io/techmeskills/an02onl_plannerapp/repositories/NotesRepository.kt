package io.techmeskills.an02onl_plannerapp.repositories

import io.techmeskills.an02onl_plannerapp.database.dao.NotesDao
import io.techmeskills.an02onl_plannerapp.datastore.AppSettings
import io.techmeskills.an02onl_plannerapp.models.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

class NotesRepository(private val notesDao: NotesDao, private val appSettings: AppSettings) {

    val currentNotesFlow: Flow<List<Note>> = appSettings.userIdFlow()
        .flatMapLatest { userId -> notesDao.getCurrentNotesLiveFlow(userId) }

    suspend fun saveNote(note: Note) {
        withContext(Dispatchers.IO) {
            notesDao.saveNote(
                Note(
                    title = note.title,
                    date = note.date,
                    userId = appSettings.userId()
                )
            )
        }
    }

    suspend fun getCurrentUserNotes(): List<Note> {
        return notesDao.getAllNotesByUserId(appSettings.userId())
    }

    suspend fun setAllNotesSyncWithCloud() {
        withContext(Dispatchers.IO) {
            notesDao.setAllNotesSyncWithCloud()
        }
    }

    suspend fun saveNotes(notes: List<Note>) {
        withContext(Dispatchers.IO) {
            notesDao.saveNotes(notes)
        }
    }

    suspend fun updateNote(note: Note) {
        withContext(Dispatchers.IO) {
            notesDao.updateNote(note)
        }
    }

    suspend fun checkImportedNote(notes: MutableList<Note>, userId: Long): List<Note> {
        withContext(Dispatchers.IO) {
            val notesDB = notesDao.getAllNotesByUserId(userId)
            for (noteDB in notesDB) {
                if (notes.isEmpty()) {
                    break
                }
                for (noteCloud in notes) {
                    if (noteCloud.title == noteDB.title && noteCloud.date == noteDB.date) {
                        notes.remove(noteCloud)
                        break
                    }
                }
            }
        }
        return notes.toList()
    }

    suspend fun deleteNote(note: Note) {
        withContext(Dispatchers.IO) {
            notesDao.deleteNote(note)
        }
    }
}