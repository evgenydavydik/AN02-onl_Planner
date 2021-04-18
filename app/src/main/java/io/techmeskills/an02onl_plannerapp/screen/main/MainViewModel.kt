package io.techmeskills.an02onl_plannerapp.screen.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import io.techmeskills.an02onl_plannerapp.database.dao.NotesDao
import io.techmeskills.an02onl_plannerapp.models.Note
import io.techmeskills.an02onl_plannerapp.support.CoroutineViewModel
import kotlinx.coroutines.launch

class MainViewModel(private val notesDao: NotesDao) : CoroutineViewModel() {

    val notesLiveData = notesDao.getAllNotesLiveData().map {
        listOf(AddNewNote) + it
    }

    fun deleteNote(note: Note) {
        launch {
            notesDao.deleteNote(note)
        }
    }
}

object AddNewNote : Note(-1, "", "")





