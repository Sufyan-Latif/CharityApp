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
import com.androidkotlin.theta.android.charityapp.activities.DonorHomeActivity
import com.androidkotlin.theta.android.charityapp.activities.LoginActivity
import com.androidkotlin.theta.android.charityapp.models.Acceptor
import com.androidkotlin.theta.android.charityapp.models.Donor
import com.androidkotlin.theta.android.charityapp.utils.Constant
import kotlinx.android.synthetic.main.fragment_account_info.*

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

        val done: Button = myView.findViewById(R.id.btn_done)
        val back: Button = myView.findViewById(R.id.btn_back)

        val bundle = this.arguments
        when (Constant.signUpModel){

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
                //TODO: CREATE ORGANIZATION HOME ACTIVITY
            }
        }

        back.setOnClickListener {
            activity!!.onBackPressed()
        }

        return myView
    }

    private fun validateUsername() : Boolean {
        username = et_username.text.toString().trim()

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
        password = et_password.text.toString().trim()

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
        password = et_password.text.toString().trim()
        conPassword = et_con_password.text.toString().trim()
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
