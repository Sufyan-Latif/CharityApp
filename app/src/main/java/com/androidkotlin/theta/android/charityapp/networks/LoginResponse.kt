package com.androidkotlin.theta.android.charityapp.networks

import com.androidkotlin.theta.android.charityapp.models.Acceptor
import com.androidkotlin.theta.android.charityapp.models.Donor

data class LoginResponse (
        val code: String,
        val message: String,
        val type: String,
        val donor: Donor,
        val acceptor: Acceptor
)