package com.androidkotlin.theta.android.charityapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Donor(
        @SerializedName("first_name")
        @Expose
        var firstName:String?="",

        @SerializedName("last_name")
        @Expose
        var lastName:String?="",

        @SerializedName("contact")
        @Expose
        var contactNum:String?="",

        @Expose
        var address:String?="",

        @SerializedName("dob")
        @Expose
        var dateOfBirth: String?="",

        @Expose
        var gender: String?="",

        @Expose
        var username:String?="",

        @Expose
        var password: String?="") : Serializable