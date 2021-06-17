package io.techmeskills.an02onl_plannerapp.screen.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import io.techmeskills.an02onl_plannerapp.database.dao.NotesDao
import io.techmeskills.an02onl_plannerapp.database.dao.UsersDao
import io.techmeskills.an02onl_plannerapp.models.Note
import io.techmeskills.an02onl_plannerapp.repositories.CloudRepository
import io.techmeskills.an02onl_plannerapp.repositories.NotesRepository
import io.techmeskills.an02onl_plannerapp.repositories.UsersRepository
import io.techmeskills.an02onl_plannerapp.support.CoroutineViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val notesRepository: NotesRepository,
    private val usersRepository: UsersRepository,
    private val cloudRepository: CloudRepository
) : CoroutineViewModel() {

    val progressLiveData = MutableLiveData<Boolean>()
    var notesLiveData = notesRepository.currentNotesFlow.asLiveData()

    fun deleteNote(note: Note) {
        launch {
            notesRepository.deleteNote(note)
        }
    }

    fun updateNote(note: Note) {
        launch {
            notesRepository.updateNote(note)
        }
    }

    fun logout() {
        launch {
            usersRepository.logout()
        }
    }

    fun sortNotes() {
        launch {
            notesRepository.sortNotesByTitle()
            notesLiveData = notesRepository.currentNotesFlow.asLiveData()
        }
    }

    fun sortNotesDate() {
        launch {
            notesRepository.sortNotesByDate()
            notesLiveData = notesRepository.currentNotesFlow.asLiveData()
        }
    }

    fun deleteUser() {
        launch {
            usersRepository.deleteUser()
        }
    }

    fun exportNotes() = launch {
        val result = cloudRepository.exportNotes()
        progressLiveData.postValue(result)
    }

    fun importNotes() = launch {
        val result = cloudRepository.importNotes()
        progressLiveData.postValue(result)
    }
}





