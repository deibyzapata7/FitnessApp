package com.example.fitnessapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [exerciceEntity::class], version = 1)
abstract class appDatabase : RoomDatabase() {


    abstract fun exerciceDao(): exerciceDao

    companion object {
        @Volatile
        private var INSTANCE: appDatabase? = null


        fun obtenirBaseDeDonnees(context: Context): appDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    appDatabase::class.java,
                    "fitness_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

