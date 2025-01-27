package com.example.mynotes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class NoteActivity : AppCompatActivity() {
    companion object {
        private const val EXTRA_ID = "EXTRA_ID"

        fun open(context: Context, noteId: Long) {
            val intent = Intent(context, NoteActivity::class.java)
            intent.putExtra(EXTRA_ID, noteId)
            context.startActivity(intent)
        }
    }

    private val noteId by lazy { intent.getLongExtra(EXTRA_ID, -1L) }
    private val db by lazy { DbInstance.getDb(application) }

    private var title: EditText? = null
    private var text: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_note)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (noteId == -1L) {
            finish()
        }

        title = findViewById<EditText>(R.id.activity_note_title)
        text = findViewById<EditText>(R.id.activity_note_text)

        lifecycleScope.launch {
            val note = db.getNote(noteId)
            title?.setText(note.title)
            text?.setText(note.text)
        }

        findViewById<ImageButton>(R.id.imageButton).setOnClickListener {
            finish()
        }

        findViewById<ImageButton>(R.id.delete).setOnClickListener {
            lifecycleScope.launch {
                db.delete(NoteEntity(noteId, "", ""))
                finish()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (noteId != -1L && title != null && text != null) {
            lifecycleScope.launch {
                db.update(
                    NoteEntity(
                        noteId,
                        title!!.text.toString(),
                        text!!.text.toString()
                    )
                )
            }
        }
    }
}