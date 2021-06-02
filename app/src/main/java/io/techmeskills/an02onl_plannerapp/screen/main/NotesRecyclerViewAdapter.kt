package io.techmeskills.an02onl_plannerapp.screen.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.techmeskills.an02onl_plannerapp.R
import io.techmeskills.an02onl_plannerapp.models.Note
import java.text.SimpleDateFormat
import java.util.*

class NotesRecyclerViewAdapter(
    private val onClick: (Note) -> Unit,
    private val onDelete: (Note) -> Unit
) : ListAdapter<Note, NotesRecyclerViewAdapter.NoteViewHolder>(NoteAdapterDiffCallback()) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoteViewHolder = NoteViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.note_list_item, parent, false),
        ::onItemClick,
        ::onItemDelete
    )

    private fun onItemClick(position: Int) {
        onClick(getItem(position))
    }

    private fun onItemDelete(position: Int) {
        onDelete(getItem(position))
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class NoteViewHolder(
        itemView: View,
        private val onItemClick: (Int) -> Unit,
        private val onItemDelete: (Int) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvDate = itemView.findViewById<TextView>(R.id.tvDate)
        private val ivCloud = itemView.findViewById<ImageView>(R.id.ivCloud)
        private val ivAlarm = itemView.findViewById<ImageView>(R.id.ivAlarm)

        init {
            itemView.setOnClickListener {
                onItemClick(adapterPosition)
            }
            itemView.setOnLongClickListener {
                onItemDelete(adapterPosition)
                false
            }
        }

        fun bind(item: Note) {
            tvTitle.text = item.title
            tvDate.text = dateFormatter.format(item.date)
            ivCloud.isVisible = item.fromCloud
            ivAlarm.isVisible = item.alarmEnabled
        }
    }
    companion object {
        private val dateFormatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    }
}

class NoteAdapterDiffCallback : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.date == newItem.date && oldItem.title == newItem.title && oldItem.fromCloud == newItem.fromCloud && oldItem.alarmEnabled == newItem.alarmEnabled
    }
}

