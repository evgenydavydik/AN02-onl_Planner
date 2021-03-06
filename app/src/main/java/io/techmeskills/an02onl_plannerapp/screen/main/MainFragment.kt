package io.techmeskills.an02onl_plannerapp.screen.main

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import io.techmeskills.an02onl_plannerapp.R
import io.techmeskills.an02onl_plannerapp.databinding.FragmentMainBinding
import io.techmeskills.an02onl_plannerapp.databinding.TestBinding
import io.techmeskills.an02onl_plannerapp.models.Note
import io.techmeskills.an02onl_plannerapp.support.*
import okhttp3.internal.notify
import okhttp3.internal.notifyAll
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class MainFragment : NavigationFragment<TestBinding>(R.layout.test) {

    override val viewBinding: TestBinding by viewBinding()

    private val viewModel: MainViewModel by viewModel()

    private val adapter = NotesRecyclerViewAdapter(
        onClick = ::onItemClick,
        onDelete = ::onItemDelete
    )

    val dayFormatter = SimpleDateFormat("dd EEE", Locale.getDefault())

    private fun onItemClick(note: Note) {
        findNavController().navigateSafe(MainFragmentDirections.toNoteDetails(note))
    }

    private fun onItemDelete(note: Note) {
        viewModel.deleteNote(note)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.recyclerView.adapter = adapter
        viewModel.notesLiveData.observe(this.viewLifecycleOwner) {
            adapter.submitList(it)
        }

        val swipeHandler = object : SwipeToDeleteCallback(this.requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                onItemDelete(viewModel.notesLiveData.value!![viewHolder.adapterPosition])
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(viewBinding.recyclerView)

        viewBinding.ivCloud.setOnClickListener {
            showCloudDialog()
        }

        viewBinding.ivSettings.setOnClickListener {
            showSettingsDialog()
        }

        viewBinding.addNote.setOnClickListener {
            findNavController().navigateSafe(MainFragmentDirections.toNoteDetails(null))
        }

        viewBinding.sort.setOnClickListener {
            viewModel.sortNotes()
            viewModel.notesLiveData.observe(this.viewLifecycleOwner) {
                adapter.submitList(it)
            }
        }

        viewBinding.sortDate.setOnClickListener {
            viewModel.sortNotesDate()
            viewModel.notesLiveData.observe(this.viewLifecycleOwner) {
                adapter.submitList(it)
            }
        }

        viewBinding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                adapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                adapter.filter.filter(s)
                return true
            }
        })

        viewModel.progressLiveData.observe(this.viewLifecycleOwner) { success ->
            if (success.not()) {
                Toast.makeText(requireContext(), R.string.cloud_failed, Toast.LENGTH_LONG)
                    .show()
            }
            //viewBinding.progressIndicator.isVisible = false
        }
    }

    private fun showSettingsDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.settings_request_title)
            .setMessage(R.string.setting_action)
            .setPositiveButton(R.string.action_logout) { dialog, _ ->
                //viewBinding.progressIndicator.isVisible = true
                viewModel.logout()
                dialog.cancel()
                findNavController().navigateSafe(MainFragmentDirections.toLoginFragment())
            }.setNegativeButton(R.string.action_delete_user) { dialog, _ ->
                //viewBinding.progressIndicator.isVisible = true
                viewModel.deleteUser()
                dialog.cancel()
                findNavController().navigateSafe(MainFragmentDirections.toLoginFragment())
            }.show()
    }

    private fun showCloudDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.cloud_request_title)
            .setMessage(R.string.pick_action)
            .setPositiveButton(R.string.action_import) { dialog, _ ->
                //viewBinding.progressIndicator.isVisible = true
                viewModel.importNotes()
                dialog.cancel()
            }.setNegativeButton(R.string.action_export) { dialog, _ ->
                //viewBinding.progressIndicator.isVisible = true
                viewModel.exportNotes()
                dialog.cancel()
            }.show()
    }

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
        viewBinding.toolbar.setVerticalMargin(marginTop = top)
        viewBinding.recyclerView.setPadding(0, 0, 0, bottom)
    }

    override val backPressedCallback: OnBackPressedCallback
        get() = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
}