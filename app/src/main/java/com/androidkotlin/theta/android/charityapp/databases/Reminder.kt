package com.androidkotlin.theta.android.charityapp.databases

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "reminders")
data class Reminder (

        @PrimaryKey(autoGenerate = true)
        val id: Int= 0,

        val time: String,

        val instructions: String? = null,

        val amount: Int? = null
) : Serializable
