package com.example.stajgunlugum.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        ActivityEntity::class,
        ReportEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun activityDao(): ActivityDao

    abstract fun reportDao(): ReportDao


    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null


        fun getDatabase(
            context: Context
        ): AppDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "staj_gunlugum_database"
                )

                    .fallbackToDestructiveMigration()

                    .build()


                INSTANCE = instance

                instance
            }
        }
    }
}