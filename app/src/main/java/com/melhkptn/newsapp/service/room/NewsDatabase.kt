package com.melhkptn.newsapp.service.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.melhkptn.newsapp.model.Article

@Database(entities = [Article::class], version = 2)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao() : NewsDAO

    companion object {

        @Volatile private var instance : NewsDatabase? = null

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                NewsDatabase::class.java,
                "newsDatabase"
            ).build()
    }
}