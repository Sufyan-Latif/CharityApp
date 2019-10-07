package com.androidkotlin.theta.android.charityapp.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Acceptor(
        @SerializedName("first_name")
        var firstName:String?="",

        @SerializedName("last_name")
        var lastName:String?="",

        @SerializedName("contact")
        var contactNum:String?="",

        var address:String?="",

        @SerializedName("dob")
        var dateOfBirth: String?="",

        var gender: String?="",

        var username:String?="",

        var password: String?="") : Serializable