package io.techmeskills.an02onl_plannerapp.screen.note_details

import io.techmeskills.an02onl_plannerapp.database.dao.NotesDao
import io.techmeskills.an02onl_plannerapp.database.dao.UsersDao
import io.techmeskills.an02onl_plannerapp.models.Note
import io.techmeskills.an02onl_plannerapp.repositories.NotesRepository
import io.techmeskills.an02onl_plannerapp.support.CoroutineViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoteDetailsViewModel(private val notesRepository: NotesRepository) : CoroutineViewModel() {

    fun addNewNote(note: Note) {
        launch {
            notesRepository.saveNote(note)
        }
    }

    fun updateNote(note: Note) {
        launch {
            notesRepository.updateNote(note)
        }
    }
}