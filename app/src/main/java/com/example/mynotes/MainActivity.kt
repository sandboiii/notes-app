package com.example.mynotes

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val db by lazy { DbInstance.getDb(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val adapter = NotesAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        findViewById<View>(R.id.fab).setOnClickListener {
            lifecycleScope.launch {
                val id = db.insert(NoteEntity(0, "New title", "New text"))
                NoteActivity.open(this@MainActivity, id)
            }
        }

        lifecycleScope.launch {
            db.getNotesFlow()
                .collect { notes: List<NoteEntity> ->
                    adapter.updateNotes(
                        notes.map { n -> Note(n.id, n.title, n.text) }
                    )
                }
        }
    }
}