package com.androidkotlin.theta.android.charityapp.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.androidkotlin.theta.android.charityapp.R
import com.androidkotlin.theta.android.charityapp.utils.SharedPrefs

class SplashActivity : AppCompatActivity() {

    private var mDelayHandler: Handler? = null
    private var SPLASH_DELAY=3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val sharedPrefs = SharedPrefs.getSharedPrefs(this)
        val type = sharedPrefs?.getString("type", "")

        var intent= Intent(this, LoginActivity::class.java)
        if (type.equals("donor"))
            intent= Intent(this, DonorHomeActivity::class.java)
        else if (type.equals("acceptor"))
            intent= Intent(this, AcceptorWelcomeActivity::class.java)

        mDelayHandler = Handler()
        val mRunnable = Runnable {
            startActivity(intent)
            finish()
        }
        mDelayHandler?.postDelayed(mRunnable, SPLASH_DELAY.toLong())
    }
}
