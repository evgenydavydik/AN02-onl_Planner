package io.techmeskills.an02onl_plannerapp.screen.note_details

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import io.techmeskills.an02onl_plannerapp.R
import io.techmeskills.an02onl_plannerapp.databinding.FragmentNoteDetailsBinding
import io.techmeskills.an02onl_plannerapp.models.Note
import io.techmeskills.an02onl_plannerapp.support.DatePickerView
import io.techmeskills.an02onl_plannerapp.support.NavigationFragment
import io.techmeskills.an02onl_plannerapp.support.setVerticalMargin
import org.koin.android.viewmodel.ext.android.viewModel
import petrov.kristiyan.colorpicker.ColorPicker
import petrov.kristiyan.colorpicker.ColorPicker.OnChooseColorListener
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NoteDetailsFragment :
    NavigationFragment<FragmentNoteDetailsBinding>(R.layout.fragment_note_details) {

    private val dateFormatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())

    override val viewBinding: FragmentNoteDetailsBinding by viewBinding()

    private val viewModel: NoteDetailsViewModel by viewModel()

    private val args: NoteDetailsFragmentArgs by navArgs()

    private var colorNote = "#FFFFFF"

    private var selectedDate: Calendar = Calendar.getInstance().apply { time = Date() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewBinding.confirm.setOnClickListener {
            if (viewBinding.etNote.text.isNotBlank()) {
                args.note?.let { //если note != null, то это обновление заметки
                    viewModel.updateNote(
                        Note(
                            id = it.id, //при обновлении надо указать id, чтобы база знала что обновлять
                            title = viewBinding.etNote.text.toString(),
                            date = selectedDate.timeInMillis,
                            userName = it.userName,
                            alarmEnabled = viewBinding.alarmSwitch.isChecked,
                            colorNote = if (colorNote == "#FFFFFF")
                                args.note!!.colorNote else colorNote,
                            pin = args.note!!.pin

                        )
                    )
                } ?: kotlin.run { //если note == null, то это новая заметка, и мы ее добавляем
                    viewModel.addNewNote(
                        Note( //при добавлении id можно не указывать
                            title = viewBinding.etNote.text.toString(),
                            date = selectedDate.timeInMillis,
                            alarmEnabled = viewBinding.alarmSwitch.isChecked,
                            userName = "",
                            colorNote = colorNote
                        )
                    )
                }
                findNavController().popBackStack()
            } else {
                Toast.makeText(requireContext(), " Please, enter your note", Toast.LENGTH_LONG)
                    .show()
            }
        }

        args.note?.let { note ->
            viewBinding.alarmSwitch.isChecked = note.alarmEnabled
            viewBinding.etNote.setText(note.title)
            selectedDate = Calendar.getInstance().apply { time = Date(note.date) }
            viewBinding.calendarView.selectedDate = selectedDate.time
        }
        viewBinding.calendarView.onDateChangeCallback = object : DatePickerView.DateChangeListener {
            override fun onDateChanged(date: Date) {
                val hour = selectedDate.get(Calendar.HOUR_OF_DAY)
                val minutes = selectedDate.get(Calendar.MINUTE)
                selectedDate.time = date
                selectedDate.set(Calendar.HOUR_OF_DAY, hour)
                selectedDate.set(Calendar.MINUTE, minutes)
            }
        }
        viewBinding.timePicker.setOnTimeChangedListener { timePicker, hour, minutes ->
            selectedDate.set(Calendar.HOUR_OF_DAY, hour)
            selectedDate.set(Calendar.MINUTE, minutes)
        }
        viewBinding.btnColor.setOnClickListener {
            val colorPicker = ColorPicker(context as Activity?)
            val colors = resources.getStringArray(R.array.colorsArray)
            val arrayColor: ArrayList<String> = ArrayList()
            colors.map { arrayColor.add(it.toString()) }

            colorPicker.setDefaultColorButton(Color.parseColor(args.note?.colorNote ?: colorNote))
                .setColors(arrayColor)
                .setColumns(5)
                .setRoundColorButton(true)
                .setOnChooseColorListener(object : OnChooseColorListener {
                    override fun onChooseColor(position: Int, color: Int) {
                        Log.d(
                            "positionColor",
                            "" + position + " " + arrayColor[position]
                        ) // will be fired only when OK button was tapped
                        colorNote = arrayColor[position]
                    }

                    override fun onCancel() {}
                })
                .show()
        }

    }

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
        viewBinding.toolbar.setVerticalMargin(marginTop = top)
        viewBinding.confirm.setVerticalMargin(marginBottom = bottom * 3 / 2)
    }

    override val backPressedCallback: OnBackPressedCallback
        get() = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
}

