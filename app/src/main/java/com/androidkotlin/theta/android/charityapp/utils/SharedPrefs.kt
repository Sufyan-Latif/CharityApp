package com.androidkotlin.theta.android.charityapp.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPrefs() {

    companion object{
        val charityPrefs = "com.androidkotlin.theta.android.charityapp"

        public fun getSharedPrefs(context: Context): SharedPreferences? {
            val sharedPrefs = context.getSharedPreferences(charityPrefs, 0)
            return sharedPrefs
        }
    }
}