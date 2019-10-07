package com.androidkotlin.theta.android.charityapp.networks

import com.androidkotlin.theta.android.charityapp.utils.Constant
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface CharityService {

    /*@FormUrlEncoded
    @POST("signup.php")
    fun signupUser(
            @Field("type") type: String,
            @Field("username") username: String,
            @Field("password") password: String,
            @Field("first_name") firstName: String,
            @Field("last_name") lastName: String,
            @Field("address") address: String,
            @Field("gender") gender: String,
            @Field("contact") contactNum: String,
            @Field("dob") dateOfBirth: String
    ): Call<SignupResponse>*/

    @FormUrlEncoded
    @POST("signup_donor.php")
    fun signupDonor(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("first_name") firstName: String,
        @Field("last_name") lastName: String,
        @Field("address") address: String,
        @Field("gender") gender: String,
        @Field("contact") contactNum: String,
        @Field("dob") dateOfBirth: String
//            @Body donor: Donor
    ): Call<SignupResponse>

    @FormUrlEncoded
    @POST("signup_acceptor.php")
    fun signupAcceptor(
            @Field("username") username: String,
            @Field("password") password: String,
            @Field("first_name") firstName: String,
            @Field("last_name") lastName: String,
            @Field("address") address: String,
            @Field("gender") gender: String,
            @Field("contact") contactNum: String,
            @Field("dob") dateOfBirth: String
//            @Body donor: Donor
    ): Call<SignupResponse>

    @FormUrlEncoded
    @POST("login.php")
    fun login(
            @Field("username") username: String,
            @Field("password") password: String
    ): Call<LoginResponse>

    companion object {
        private var retrofit: Retrofit? = null
        fun getRetrofitInstance(): CharityService? {
            if (retrofit == null){
                val gson: Gson = GsonBuilder().setLenient().create()
                retrofit = Retrofit.Builder()
                        .baseUrl(Constant.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build()
            }
            return retrofit?.create(CharityService::class.java)
        }
    }
}