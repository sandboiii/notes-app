package com.example.mynotes

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

const val DATABASE_NAME = "my_notes_db"

@Database(
    entities = [
        NoteEntity::class,
    ],
    version = 1,
)
abstract class NotesDb : RoomDatabase() {
    abstract fun getNotesDao(): NotesDao
}

@Dao
interface NotesDao {
    @Query("SELECT * FROM ${NoteEntity.TABLE_NAME}")
    fun getNotesFlow(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM ${NoteEntity.TABLE_NAME} WHERE id = :id")
    suspend fun getNote(id: Long): NoteEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: NoteEntity): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(note: NoteEntity): Int

    @Delete
    suspend fun delete(note: NoteEntity)
}