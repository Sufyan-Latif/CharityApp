package com.androidkotlin.theta.android.charityapp.models

import java.io.Serializable

data class Acceptor(var firstName:String?="", var lastName:String?="", var contactNum:String?="", var address:String?="",
                    var dateOfBirth: String?="", var gender: String?="",
                    var username:String?="", var password: String?="") : Serializable