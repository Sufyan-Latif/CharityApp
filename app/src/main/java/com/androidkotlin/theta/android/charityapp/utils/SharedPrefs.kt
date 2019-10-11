package com.androidkotlin.theta.android.charityapp.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPrefs {

    companion object{
        private const val charityPrefs = "com.androidkotlin.theta.android.charityapp"

        fun getSharedPrefs(context: Context): SharedPreferences? {
            return context.getSharedPreferences(charityPrefs, 0)
        }
    }
}