package com.example.mynotes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mynotes.NoteEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID_COLUMN)
    val id: Long = 0,
    @ColumnInfo(name = TITLE_COLUMN)
    val title: String = "",
    @ColumnInfo(name = TEXT_COLUMN)
    val text: String = "",
) {
    companion object {
        const val TABLE_NAME = "notes"

        private const val ID_COLUMN = "id"
        private const val TITLE_COLUMN = "title"
        private const val TEXT_COLUMN = "text"
    }
}