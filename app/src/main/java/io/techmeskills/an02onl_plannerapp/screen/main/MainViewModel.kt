package io.techmeskills.an02onl_plannerapp.screen.main

import androidx.lifecycle.MutableLiveData
import io.techmeskills.an02onl_plannerapp.support.CoroutineViewModel
import kotlinx.coroutines.launch

class MainViewModel : CoroutineViewModel() {

    private val notes = mutableListOf(
            Note("Помыть посуду"),
            Note("Забрать пальто из химчистки", "23.03.2021"),
            Note("Позвонить Ибрагиму"),
            Note("Заказать перламутровые пуговицы"),
            Note("Избить соседа за шум ночью"),
            Note("Выпить на неделе с Володей", "22.03.2021"),
            Note("Починить кран"),
            Note("Выбить ковры перед весной"),
            Note("Заклеить сапог жене"),
            Note("Купить картошки"),
            Note("Скачать кино в самолёт", "25.03.2021")
    )

    val listLifeData = MutableLiveData<List<Note>>(notes)


    fun addNote(text: String, date: String? = null) {
        launch {
            notes.add(0, Note(text, date))
            listLifeData.postValue(notes)
        }
    }

    fun editNote(position: Int, text: String, date: String? = null) {
        launch {
            notes.set(position, Note(text, date))
            listLifeData.postValue(notes)
        }
    }

    fun remoteNote(position: Int){
        launch {
            notes.removeAt(position)
            listLifeData.postValue(notes)
        }
    }

}

class Note(
        val title: String,
        val date: String? = null
)
