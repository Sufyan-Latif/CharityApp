package com.androidkotlin.theta.android.charityapp.databases

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = arrayOf(Reminder::class), version = 1, exportSchema = false)
abstract class CharityDatabase: RoomDatabase() {

    abstract fun reminderDao(): ReminderDao

    companion object{

        @Volatile
        private var DB_INSTANCE: CharityDatabase? = null

        private val DATABASE_NAME = "charity_database.db"

        fun getCharityDatabase(context: Context):CharityDatabase?{

            if (DB_INSTANCE == null){
                synchronized(this){
                    DB_INSTANCE = Room.databaseBuilder(
                            context,
                            CharityDatabase::class.java,
                            DATABASE_NAME)
                            .allowMainThreadQueries()
                            .build()
                }
            }
            return DB_INSTANCE
        }
    }
}