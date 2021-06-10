package io.techmeskills.an02onl_plannerapp.repositories

import io.techmeskills.an02onl_plannerapp.database.dao.NotesDao
import io.techmeskills.an02onl_plannerapp.datastore.AppSettings
import io.techmeskills.an02onl_plannerapp.models.Note
import io.techmeskills.an02onl_plannerapp.repository.NotificationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

class NotesRepository(
    private val notesDao: NotesDao,
    private val appSettings: AppSettings,
    private val notificationRepository: NotificationRepository
) {

    private var click = 0
    var currentNotesFlow: Flow<List<Note>> = appSettings.userNameFlow()
        .flatMapLatest { userName -> notesDao.getCurrentNotesLiveFlow(userName) }

    suspend fun saveNote(note: Note) {
        withContext(Dispatchers.IO) {
            val id = notesDao.saveNote(
                note.copy(userName = appSettings.userName())
            )
            if (note.alarmEnabled) {
                notificationRepository.setNotification(note.copy(id = id))
            }
        }
    }

    suspend fun getCurrentUserNotes(): List<Note> {
        return notesDao.getAllNotesByUserId(appSettings.userName())
    }

    suspend fun setAllNotesSyncWithCloud() {
        withContext(Dispatchers.IO) {
            notesDao.setAllNotesSyncWithCloud()
        }
    }

    suspend fun sortNotesByTitle() {
        withContext(Dispatchers.IO) {

            if (click == 0) {
                currentNotesFlow = appSettings.userNameFlow().flatMapLatest { userName ->
                    notesDao.getCurrentNotesLiveFlowSortNotesTitleDesc(userName)
                }
                click += 1
            } else {
                currentNotesFlow = appSettings.userNameFlow().flatMapLatest { userName ->
                    notesDao.getCurrentNotesLiveFlowSortNotesTitleAsc(userName)
                }
                click -= 1
            }

        }
    }

    suspend fun sortNotesByDate() {
        withContext(Dispatchers.IO) {

            if (click == 0) {
                currentNotesFlow = appSettings.userNameFlow().flatMapLatest { userName ->
                    notesDao.getCurrentNotesLiveFlowSortNotesDateDesc(userName)
                }
                click += 1
            } else {
                currentNotesFlow = appSettings.userNameFlow().flatMapLatest { userName ->
                    notesDao.getCurrentNotesLiveFlowSortNotesDateAsc(userName)
                }
                click -= 1
            }

        }
    }

    suspend fun saveNotes(notes: List<Note>) {
        withContext(Dispatchers.IO) {
            notesDao.saveNotes(notes)
            notes.forEach {
                if (it.alarmEnabled) {
                    notificationRepository.setNotification(it)
                }
            }
        }
    }

    suspend fun updateNote(note: Note) {
        withContext(Dispatchers.IO) {
            notesDao.getNoteById(note.id)?.let { oldNote ->
                notificationRepository.unsetNotification(oldNote)
            }
            notesDao.updateNote(note)
            if (note.alarmEnabled) {
                notificationRepository.setNotification(note)
            }
        }
    }

    suspend fun checkImportedNote(notes: MutableList<Note>, userName: String): List<Note> {
        withContext(Dispatchers.IO) {
            val notesDB = notesDao.getAllNotesByUserId(userName)
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
            notificationRepository.unsetNotification(note)
            notesDao.deleteNote(note)
        }
    }

    suspend fun deleteNoteById(noteId: Long) {
        withContext(Dispatchers.IO) {
            notesDao.getNoteById(noteId)?.let {
                notificationRepository.unsetNotification(it)
                notesDao.deleteNote(it)
            }
        }
    }

    suspend fun postponeNoteById(noteId: Long) {
        withContext(Dispatchers.IO) {
            notesDao.getNoteById(noteId)?.let { note ->
                notificationRepository.unsetNotification(note)
                val postponedNote = notificationRepository.postponeNoteTimeByFiveMins(note)
                notesDao.updateNote(postponedNote)
                notificationRepository.setNotification(postponedNote)
            }
        }
    }
}