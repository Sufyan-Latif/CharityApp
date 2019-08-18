package com.androidkotlin.theta.android.charityapp.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.androidkotlin.theta.android.charityapp.R
import com.androidkotlin.theta.android.charityapp.fragments.SignupPersonalInfoFragment
import com.androidkotlin.theta.android.charityapp.utils.Constant

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        Log.d("Frag1", "Sign up Activity : "+Constant.signUpModel)

        title = "Sign up"

        if (Constant.signUpModel == "acceptor" || Constant.signUpModel == "donor"){
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.signup_container, SignupPersonalInfoFragment())
            fragmentTransaction.commit()
        }


/*
        val cal = Calendar.getInstance()
        val year: Int = cal.get(Calendar.YEAR)
        val month: Int = cal.get(Calendar.MONTH)
        val day: Int = cal.get(Calendar.DAY_OF_MONTH)

        val dialog= DatePickerDialog(
                this@SignupActivity,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
//                android.R.style.Widget_PopupWindow,
                DatePickerDialog.OnDateSetListener { _, year, month, day ->
                    et_dob.setText("$day/${month+1}/$year")
                },
                year, month, day
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        et_dob.setOnClickListener {
            dialog.show()
        }*/
    }
}
