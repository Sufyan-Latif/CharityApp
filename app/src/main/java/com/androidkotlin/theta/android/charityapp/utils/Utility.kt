package com.androidkotlin.theta.android.charityapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.support.v4.content.ContextCompat.getSystemService

class Utility {
    companion object{
        fun isInternetConnected(context: Context): Boolean{
//            val connectivityManager = getSystemService(context) as ConnectivityManager
//            return connectivityManager.activeNetworkInfo != null
            return true
        }
    }

}