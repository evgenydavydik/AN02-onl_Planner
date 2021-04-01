package io.techmeskills.an02onl_plannerapp.screen.main

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import io.techmeskills.an02onl_plannerapp.support.CoroutineViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel : CoroutineViewModel() {
    var text: String = ""

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


    fun doClickButton() {
        launch {
            addNote(text)
            listLifeData.postValue(notes)
        }
    }


    private suspend fun addNote(text: String) {
        notes.add(Note(text))
    }
}

class Note(
        val title: String,
        val date: String? = null
)
