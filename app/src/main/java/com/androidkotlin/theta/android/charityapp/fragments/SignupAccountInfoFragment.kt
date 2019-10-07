package com.androidkotlin.theta.android.charityapp.fragments


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast

import com.androidkotlin.theta.android.charityapp.R
import com.androidkotlin.theta.android.charityapp.activities.AcceptorWelcomeActivity
import com.androidkotlin.theta.android.charityapp.activities.DonorHomeActivity
import com.androidkotlin.theta.android.charityapp.activities.LoginActivity
import com.androidkotlin.theta.android.charityapp.models.Acceptor
import com.androidkotlin.theta.android.charityapp.models.Donor
import com.androidkotlin.theta.android.charityapp.networks.CharityService
import com.androidkotlin.theta.android.charityapp.networks.SignupResponse
import com.androidkotlin.theta.android.charityapp.utils.Constant
import com.androidkotlin.theta.android.charityapp.utils.SharedPrefs
import kotlinx.android.synthetic.main.fragment_account_info.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 *
 */
class AccountInfoFragment : Fragment() {

    private lateinit var myView: View
    private lateinit var username: String
    private lateinit var password: String
    private lateinit var conPassword: String
    private lateinit var donor: Donor
    private lateinit var acceptor: Acceptor

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        myView =  inflater.inflate(R.layout.fragment_account_info, container, false)

        val btnDone: Button = myView.findViewById(R.id.btn_done)
        val btnBack: Button = myView.findViewById(R.id.btn_back)

        val bundle = this.arguments

        btnDone.setOnClickListener {
            username = et_username.text.toString().trim()
            password = et_password.text.toString()
            conPassword = et_con_password.text.toString()
            if (validateUsername() && validatePassword() && validateConPassword()){
                Toast.makeText(activity!!.baseContext, "Condition Satisfied", Toast.LENGTH_SHORT).show()
                when (Constant.signUpModel){
                    "acceptor" -> {
                        acceptor = bundle!!.getSerializable("acceptor") as Acceptor
                        acceptor.username = username
                        acceptor.password = password
                        signupAcceptor(acceptor)
                    }
                    "donor" -> {
                        donor = bundle!!.getSerializable("donor") as Donor
                        donor.username = username
                        donor.password = password
                        signupDonor(donor)
                    }
                    "organization" -> {
                        //TODO: CREATE ORGANIZATION HOME ACTIVITY
                    }
                }
            }
        }
        /*when (Constant.signUpModel){

            "acceptor" ->{
                acceptor = bundle!!.getSerializable("acceptor") as Acceptor
                done.setOnClickListener {
                    if (validateUsername() && validatePassword() && validateConPassword()){
                        Toast.makeText(activity!!.baseContext, "Condition Satisfied", Toast.LENGTH_SHORT).show()
                        acceptor.username = et_username.text.toString()
                        acceptor.password = et_password.text.toString()
                        //TODO: CREATE ACCEPTOR HOME ACTIVITY
                        val intent = Intent(myView.context, LoginActivity::class.java)
                        intent.putExtra("acceptor", acceptor)
                        startActivity(intent)
                    }
                }
            }
            "donor" -> {
                donor = bundle!!.getSerializable("donor") as Donor
                done.setOnClickListener {
                    if (validateUsername() && validatePassword() && validateConPassword()){
                        Toast.makeText(activity!!.baseContext, "Login Successfully", Toast.LENGTH_SHORT).show()
                        donor.username = et_username.text.toString()
                        donor.password = et_password.text.toString()
                        val intent = Intent(myView.context, DonorHomeActivity::class.java)
                        intent.putExtra("donor", donor)
                        startActivity(intent)
                    }
                }
            }
            "organization" -> {

            }
        }*/

        btnBack.setOnClickListener {
            activity!!.onBackPressed()
        }

        return myView
    }

    private fun signupDonor(donor: Donor) {
        val service = CharityService.getRetrofitInstance()
        val call = service?.signupDonor(username = username, password = password,
                firstName = donor.firstName!!, lastName = donor.lastName!!, address = donor.address!!,
                contactNum = donor.contactNum!!, dateOfBirth = donor.dateOfBirth!!, gender = donor.gender!!)

        call?.enqueue(object : Callback<SignupResponse>{
            override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                Toast.makeText(myView.context, "Error occured" + t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<SignupResponse>, response: Response<SignupResponse>) {
                if (response.isSuccessful){
                    val body = response.body()
                    if (body?.code.equals("success")){
                        Toast.makeText(myView.context, "" + body?.message, Toast.LENGTH_LONG).show()
                        val sharedPrefs = SharedPrefs.getSharedPrefs(myView.context)
                        val editor = sharedPrefs?.edit()
                        editor?.putString("type", "donor")
                        editor?.putString("username", donor.username)
                        editor?.putString("first_name", donor.firstName)
                        editor?.putString("last_name", donor.lastName)
                        editor?.putString("address", donor.address)
                        editor?.putString("gender", donor.gender)
                        editor?.putString("contact", donor.contactNum)
                        editor?.apply()
                        val intent = Intent(myView.context, DonorHomeActivity::class.java)
                        intent.putExtra("donor", donor)
                        startActivity(intent)
                        activity!!.finish()
                    }
                    else if (body?.code.equals("failure")){
                        Toast.makeText(myView.context, "" + body?.message, Toast.LENGTH_LONG).show()
                    }
                }
            }

        })
    }

    private fun signupAcceptor(acceptor: Acceptor) {
        val service = CharityService.getRetrofitInstance()
        val call = service?.signupAcceptor(username = username, password = password,
                firstName = acceptor.firstName!!, lastName = acceptor.lastName!!, address = acceptor.address!!,
                contactNum = acceptor.contactNum!!, dateOfBirth = acceptor.dateOfBirth!!, gender = acceptor.gender!!)

        call?.enqueue(object : Callback<SignupResponse>{
            override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                Toast.makeText(myView.context, "Error occured" + t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<SignupResponse>, response: Response<SignupResponse>) {
                if (response.isSuccessful){
                    val body = response.body()
                    if (body?.code.equals("success")){
                        Toast.makeText(myView.context, "" + body?.message, Toast.LENGTH_LONG).show()
                        val sharedPrefs = SharedPrefs.getSharedPrefs(myView.context)
                        val editor = sharedPrefs?.edit()
                        editor?.putString("type", "acceptor")
                        editor?.putString("username", acceptor.username)
                        editor?.putString("first_name", acceptor.firstName)
                        editor?.putString("last_name", acceptor.lastName)
                        editor?.putString("address", acceptor.address)
                        editor?.putString("gender", acceptor.gender)
                        editor?.putString("contact", acceptor.contactNum)
                        editor?.apply()
                        val intent = Intent(myView.context, AcceptorWelcomeActivity::class.java)
                        intent.putExtra("acceptor", acceptor)
                        startActivity(intent)
                        activity!!.finish()
                    }
                    else if (body?.code.equals("failure")){
                        Toast.makeText(myView.context, "" + body?.message, Toast.LENGTH_LONG).show()
                    }
                }
            }

        })
    }

    private fun validateUsername() : Boolean {
        return if (username == ""){
            et_username.error = "Enter Username"
            false
        }
        else{
            et_username.error = null
            true
        }
    }

    private fun validatePassword() : Boolean {
        return if (password == ""){
            et_password.error = "Enter Password"
            false
        }
        else{
            et_password.error = null
            true
        }
    }
    private fun validateConPassword() : Boolean {
        return if (conPassword != password){
            et_con_password.error = "Password Mismatch"
            false
        }
        else{
            et_con_password.error = null
            true
        }
    }
}
