package io.techmeskills.an02onl_plannerapp.screen.main

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import io.techmeskills.an02onl_plannerapp.R
import io.techmeskills.an02onl_plannerapp.models.Note
import java.text.SimpleDateFormat
import java.util.*

class NotesRecyclerViewAdapter(
    private val onClick: (Note) -> Unit,
    private val onDelete: (Note) -> Unit,
    private var currentList: MutableList<Note> = mutableListOf()
) : RecyclerView.Adapter<NotesRecyclerViewAdapter.NoteViewHolder>(), Filterable {

    var noteFilterList: MutableList<Note>

    init {
        noteFilterList = currentList
    }

    fun setNewList(mutableList: MutableList<Note>) {
        currentList = mutableList
        noteFilterList = currentList
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoteViewHolder = NoteViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.note_list_item, parent, false),
        ::onItemClick,
        ::onItemDelete
    )

    private fun onItemClick(position: Int) {
        onClick(noteFilterList[position])
    }

    private fun onItemDelete(position: Int) {
        onDelete(noteFilterList[position])
    }

    override fun getItemCount(): Int {
        return if (noteFilterList.isEmpty()) {
            currentList.size
        } else {
            noteFilterList.size
        }
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        if (noteFilterList.isEmpty()) {
            holder.bind(currentList[position])
        } else {
            holder.bind(noteFilterList[position])
        }
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
        private val card = itemView.findViewById<MaterialCardView>(R.id.card)

        init {
            itemView.setOnClickListener {
                onItemClick(adapterPosition)
            }
            itemView.setOnLongClickListener {
                itemView.setBackgroundColor(Color.GREEN)
                //onItemDelete(adapterPosition)
                false
            }
        }

        fun bind(item: Note) {
            tvTitle.text = item.title
            tvDate.text = dateFormatter.format(item.date)
            ivCloud.isVisible = item.fromCloud
            ivAlarm.isVisible = item.alarmEnabled
            card.setCardBackgroundColor(Color.parseColor(item.colorNote))
        }
    }

    companion object {
        private val dateFormatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                noteFilterList = if (charSearch.isEmpty()) {
                    currentList
                } else {
                    val resultList = ArrayList<Note>()
                    for (note in currentList) {
                        if (note.title.toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT))
                        ) {
                            resultList.add(note)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = noteFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                noteFilterList = results?.values as MutableList<Note>
                notifyDataSetChanged()
            }
        }
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

