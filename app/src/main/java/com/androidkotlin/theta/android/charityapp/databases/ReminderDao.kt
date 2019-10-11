package com.androidkotlin.theta.android.charityapp.databases

import androidx.room.*

@Dao
interface ReminderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addReminder(reminder: Reminder)

    @Query("SELECT * FROM reminders")
    fun getAll(): List<Reminder>?

    @Update
    fun updateReminder(reminder: Reminder)

    @Delete
    fun deleteReminder(reminder: Reminder)
}