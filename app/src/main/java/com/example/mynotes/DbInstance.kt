package com.example.mynotes

import android.app.Application
import androidx.room.Room

object DbInstance {
    private var instance: NotesDao? = null

    fun getDb(application: Application): NotesDao {
        if (instance == null) {
            synchronized(this) {
                if (instance == null) {
                    instance = Room
                        .databaseBuilder(application, NotesDb::class.java, DATABASE_NAME)
                        .build()
                        .getNotesDao()
                }
            }
        }
        return instance!!
    }
}