package com.androidkotlin.theta.android.charityapp.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.androidkotlin.theta.android.charityapp.R
import kotlinx.android.synthetic.main.activity_pickup_service.*

class PickupServiceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pickup_service)

        etDescription.setText("")
        etAmount.setText("")
        btnDone.setOnClickListener {

        }
        btnUploadPhoto.setOnClickListener {

        }
    }
}
