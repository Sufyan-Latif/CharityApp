package com.androidkotlin.theta.android.charityapp.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.androidkotlin.theta.android.charityapp.R

class SplashActivity : AppCompatActivity() {

    private var mDelayHandler: Handler? = null
    private var SPLASH_DELAY=1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        mDelayHandler = Handler()
        val mRunnable = Runnable {
            val intent= Intent(this, DonorHomeActivity::class.java)
//            val intent= Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY.toLong())
    }
}
