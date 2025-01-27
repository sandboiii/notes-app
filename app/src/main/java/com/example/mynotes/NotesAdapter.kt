package com.example.mynotes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter: RecyclerView.Adapter<NoteViewHolder>() {
    private var notes = listOf(
        Note(0, "title", "text"),
        Note(0, "title2", "text2"),
        Note(0, "title3", "text3"),
        Note(0, "title4", "text4"),
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val noteView = LayoutInflater.from(parent.context).inflate(R.layout.note_view, parent, false)
        return NoteViewHolder(noteView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.bind(note)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun updateNotes(newNotes: List<Note>) {
        notes = newNotes
        notifyDataSetChanged()
    }
}

class NoteViewHolder(private val itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bind(note: Note) {
        val title = itemView.findViewById<TextView>(R.id.note_title)
        title.text = note.title

        val textView = itemView.findViewById<TextView>(R.id.note_text)
        textView.text = note.text

        itemView.setOnClickListener {
            NoteActivity.open(itemView.context, note.id)
        }
    }

}