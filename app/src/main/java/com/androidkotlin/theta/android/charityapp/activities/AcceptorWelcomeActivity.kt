package com.androidkotlin.theta.android.charityapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.androidkotlin.theta.android.charityapp.R
import com.androidkotlin.theta.android.charityapp.utils.SharedPrefs
import kotlinx.android.synthetic.main.activity_acceptor_welcome.*


class AcceptorWelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acceptor_welcome)

        val sharedPrefs = SharedPrefs.getSharedPrefs(this)
        val name = sharedPrefs?.getString("first_name", "") + " " + sharedPrefs?.getString("last_name", "")
        tvWelcome.text = tvWelcome.text.toString() + name

        btnLogout.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Alert!")
                    .setMessage("Are you sure you want to logout?")
                    .setPositiveButton("YES") { _, _ ->
                        run {
                            val editor = sharedPrefs?.edit()
                            editor?.clear()
                            editor?.apply()
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                    .setNegativeButton("NO") { _, _ -> }
                    .create()
                    .show()
        }
    }
}
