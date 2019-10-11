package com.androidkotlin.theta.android.charityapp.activities

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*
import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.net.ConnectivityManager
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.androidkotlin.theta.android.charityapp.R
import com.androidkotlin.theta.android.charityapp.models.Acceptor
import com.androidkotlin.theta.android.charityapp.models.Donor
import com.androidkotlin.theta.android.charityapp.networks.CharityService
import com.androidkotlin.theta.android.charityapp.networks.LoginResponse
import com.androidkotlin.theta.android.charityapp.utils.Constant
import com.androidkotlin.theta.android.charityapp.utils.SharedPrefs
import com.androidkotlin.theta.android.charityapp.utils.Utility
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_account_info.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    private var mDelayHandler: Handler? = null
    private var SPLASH_DELAY = 3000
    private var username: String? = null
    private var password: String? = null

    private lateinit var reducer: AnimatorSet
    private lateinit var resizer: AnimatorSet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        reducer = AnimatorInflater.loadAnimator(this, R.animator.reduce_size) as AnimatorSet
        resizer = AnimatorInflater.loadAnimator(this, R.animator.regain_size) as AnimatorSet
        reducer.setTarget(btn_login)
        resizer.setTarget(btn_login)

        btn_login.setOnClickListener {
            username = etUsername.text.toString()
            password = etPassword.text.toString()
            if (Utility.isInternetConnected(this)) {
                if (validateUsername() && validatePassword())
                    login(username!!, password!!)
//                    animateButton()
            } else {
                Snackbar.make(
                        coordinatorLayout,
                        getString(R.string.no_internet_connection),
                        Snackbar.LENGTH_INDEFINITE
                )
                        .setAction("Retry") {
                            btn_login.callOnClick()
                        }
                        .setActionTextColor(resources.getColor(R.color.colorButton))
                        .show()
            }
        }

        tv_register.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        tv_register.setOnClickListener {

            val intent = Intent(this@LoginActivity, SignupActivity::class.java)
            val models = arrayOf("Acceptor", "Donor"/*, "Organization"*/)
            val builder: AlertDialog.Builder = AlertDialog.Builder(this@LoginActivity)
                    .setTitle("Register as !")
                    .setItems(models)
                    { _, i ->
                        when (i) {
                            0 -> {
                                Constant.signUpModel = "acceptor"
                                startActivity(intent)
                            }
                            1 -> {
                                Constant.signUpModel = "donor"
                                startActivity(intent)
                            }
/*                            2 -> {
                                Constant.signUpModel = "organization"
                                startActivity(intent)
                            }*/
                        }
                    }
            val dialog = builder.create()
            dialog.show()
        }
    }

    private fun login(username: String, password: String) {
        setLoading(true)
        val service = CharityService.getRetrofitInstance()
        val call = service?.login(username, password)

        call?.enqueue(object : Callback<LoginResponse>{

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                setLoading(false)
                if (response.isSuccessful){
                    val body = response.body()
                    if (body?.code.equals("success")){
                        when(body?.type){
                            "donor"->{
                                val donor: Donor? = body.donor
                                val sharedPrefs = SharedPrefs.getSharedPrefs(this@LoginActivity)
                                val editor = sharedPrefs?.edit()
                                editor?.putString("type", "donor")
                                editor?.putString("username", donor?.username)
                                editor?.putString("first_name", donor?.firstName)
                                editor?.putString("last_name", donor?.lastName)
                                editor?.putString("address", donor?.address)
                                editor?.putString("gender", donor?.gender)
                                editor?.putString("contact", donor?.contactNum)
                                editor?.apply()
                                val intent = Intent(this@LoginActivity, DonorHomeActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                            "acceptor" -> {
                                val acceptor: Acceptor? = body.acceptor
                                val sharedPrefs = SharedPrefs.getSharedPrefs(this@LoginActivity)
                                val editor = sharedPrefs?.edit()
                                editor?.putString("type", "acceptor")
                                editor?.putString("username", acceptor?.username)
                                editor?.putString("first_name", acceptor?.firstName)
                                editor?.putString("last_name", acceptor?.lastName)
                                editor?.putString("address", acceptor?.address)
                                editor?.putString("gender", acceptor?.gender)
                                editor?.putString("contact", acceptor?.contactNum)
                                editor?.apply()
                                val intent = Intent(this@LoginActivity, AcceptorWelcomeActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                            else ->
                                Toast.makeText(this@LoginActivity, "Invalid username or password", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else if (response.body()?.code.equals("failure"))
                        Toast.makeText(this@LoginActivity, "Invalid username or password", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                setLoading(false)
                Toast.makeText(this@LoginActivity, "Error Occured" + t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun validateUsername(): Boolean {
        return if (etUsername.text.toString() == ""){
            etUsername.requestFocus()
            etUsername.error = "Enter Username"
            false
        }
        else {
            etUsername.error = null
            true
        }
    }

    private fun validatePassword(): Boolean {
        return if (etPassword.text.toString() == ""){
            etPassword.requestFocus()
            etPassword.error = "Enter Password"
            false
        }
        else {
            etPassword.error = null
            true
        }
    }

    private fun setLoading(isLoading: Boolean){
        if (isLoading){
            etUsername.isEnabled = false
            etPassword.isEnabled = false
            btn_login.isEnabled = false
            tv_register.isEnabled = false
            progressBar.visibility = View.VISIBLE
            reducer.start()
        }
        else{
            etUsername.isEnabled = true
            etPassword.isEnabled = true
            btn_login.isEnabled = true
            tv_register.isEnabled = true
            resizer.start()
//            progressBar.visibility = View.INVISIBLE
        }
    }

    private fun animateButton() {
        progressBar.visibility = View.VISIBLE
        reducer.start()

        mDelayHandler = Handler()
        val mRunnable = Runnable {

            resizer.start()
//            Toast.makeText(this, "Welcome " + etUsername.text, Toast.LENGTH_SHORT).show()
        }
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY.toLong())
    }

    override fun onStart() {
        super.onStart()
        if (Constant.signUpModel != "")
            Constant.signUpModel = ""
    }

    override fun onStop() {
        super.onStop()
        if (etUsername.error != "")
            etUsername.error = null

        if (etPassword.error != "")
            etPassword.error = null
    }
}
