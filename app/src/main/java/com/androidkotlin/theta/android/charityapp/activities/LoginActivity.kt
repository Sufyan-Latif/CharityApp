package com.androidkotlin.theta.android.charityapp.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*
import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.net.ConnectivityManager
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.Toast
import com.androidkotlin.theta.android.charityapp.R
import com.androidkotlin.theta.android.charityapp.utils.Constant


class LoginActivity : AppCompatActivity() {

    private var mDelayHandler: Handler? = null
    private var SPLASH_DELAY = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_login.setOnClickListener {
            if (isInternetConnected()) {
                validateUsernamePassword()
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
            val models = arrayOf("Acceptor", "Donor", "Organization")
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
                            2 -> {
                                Constant.signUpModel = "organization"
                                startActivity(intent)
                            }
                        }
                    }
            val dialog = builder.create()
            dialog.show()

            Log.d("Frag1", "Login Activity : "+Constant.signUpModel)
        }
    }

    private fun validateUsernamePassword() {
        if (etusername.text.toString() == "" || etpassword.text.toString() == "") {
            if (etusername.text.toString() == "") {
                etusername.error = "Enter Username"
            } else
                etusername.error = null

            if (etpassword.text.toString() == "") {
                etpassword.error = "Enter Password"
            } else
                etpassword.error = null
        } else {
            textInputLayoutUsername.error = null
            textInputLayoutPassword.error = null
            animateButton()
        }
    }

    private fun animateButton() {
        progressBar.visibility = View.VISIBLE
        val reducer = AnimatorInflater.loadAnimator(this, R.animator.reduce_size) as AnimatorSet
        reducer.setTarget(btn_login)
        reducer.start()

        mDelayHandler = Handler()
        val mRunnable = Runnable {
            val resizer = AnimatorInflater.loadAnimator(this, R.animator.regain_size) as AnimatorSet
            resizer.setTarget(btn_login)
            resizer.start()
            Toast.makeText(this, "Welcome " + etusername.text, Toast.LENGTH_SHORT).show()
        }
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY.toLong())
    }

    override fun onDestroy() {
        super.onDestroy()
        if (etusername.error != "")
            etusername.error = null

        if (etpassword.error != "")
            etpassword.error = null
    }

    private fun isInternetConnected(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null
    }

    override fun onStart() {
        super.onStart()
        if (Constant.signUpModel != "")
            Constant.signUpModel = ""
    }
}
