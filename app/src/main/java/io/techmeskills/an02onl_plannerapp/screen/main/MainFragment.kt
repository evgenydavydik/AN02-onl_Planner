package io.techmeskills.an02onl_plannerapp.screen.main

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import io.techmeskills.an02onl_plannerapp.R
import io.techmeskills.an02onl_plannerapp.databinding.FragmentMainBinding
import io.techmeskills.an02onl_plannerapp.screen.add_note.AddNoteFragment
import io.techmeskills.an02onl_plannerapp.support.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainFragment : NavigationFragment<FragmentMainBinding>(R.layout.fragment_main) {

    override val viewBinding: FragmentMainBinding by viewBinding()

    private val viewModel: MainViewModel by viewModel()

    private var positions: Int? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.listLifeData.observe(this.viewLifecycleOwner, {
            viewBinding.recyclerView.adapter = NotesRecyclerViewAdapter(it)
        })

        setFragmentResultListener(AddNoteFragment.ADD_NEW_RESULT) { key, bundle ->
            val note = bundle.getString(AddNoteFragment.TEXT)
            val date = bundle.getString(AddNoteFragment.DATE)
            if (positions == null) {
                note?.let {
                    viewModel.addNote(it, date)
                }
            } else {
                note?.let {
                    viewModel.editNote(positions!!, it, date)
                }
            }

        }




        viewBinding.btnAdd.setOnClickListener {
            it.findNavController().navigate(R.id.addNoteFragment)
        }

        viewBinding.recyclerView.addOnItemTouchListener(RecyclerItemClickListener(this.viewBinding.recyclerView,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onLongItemClick(child: View, position: Int) {
                        viewModel.remoteNote(position)
                    }

                    override fun onItemClick(view: View, position: Int) {
                        view.findNavController().navigate(R.id.addNoteFragment)
                        positions = position
                    }
                }))

        val swipeHandler = object : SwipeToDeleteCallback(this.requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.remoteNote(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(viewBinding.recyclerView)
    }

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
        viewBinding.toolbar.setPadding(0, top, 0, 0)
        viewBinding.recyclerView.setPadding(0, 0, 0, bottom)
        viewBinding.btnAdd.setVerticalMargin(marginBottom = bottom)
    }

    override val backPressedCallback: OnBackPressedCallback
        get() = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
}