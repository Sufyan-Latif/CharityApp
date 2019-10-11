package com.androidkotlin.theta.android.charityapp.fragments


import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

import com.androidkotlin.theta.android.charityapp.R
import com.androidkotlin.theta.android.charityapp.activities.PickupServiceActivity

/**
 * A simple [Fragment] subclass.
 */
class DonateFragment : Fragment(), View.OnClickListener {

    private lateinit var myView: View
    private lateinit var btnDonateFood: Button
    private lateinit var btnDonateClothes: Button
    private lateinit var btnDonateForKids: Button
    private lateinit var btnDonateForReligion: Button
    private lateinit var btnDonateForEducation: Button
    private lateinit var btnDonateOthers: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_donate, container, false)

        bindViews()
        setClickListeners()

        /*  btnDonateFood.setOnClickListener {
//            showDialog()
        }*/

        return myView
    }

    private fun setClickListeners() {
        btnDonateFood.setOnClickListener(this)
        btnDonateClothes.setOnClickListener(this)
        btnDonateForKids.setOnClickListener(this)
        btnDonateForReligion.setOnClickListener(this)
        btnDonateForEducation.setOnClickListener(this)
        btnDonateOthers.setOnClickListener(this)
    }

    private fun bindViews() {
        btnDonateFood = myView.findViewById(R.id.btn_donate_food)
        btnDonateClothes = myView.findViewById(R.id.btn_donate_clothes)
        btnDonateForKids = myView.findViewById(R.id.btn_donate_for_kids)
        btnDonateForReligion = myView.findViewById(R.id.btn_donate_for_religion)
        btnDonateForEducation = myView.findViewById(R.id.btn_donate_education)
        btnDonateOthers = myView.findViewById(R.id.btn_donate_others)
    }


    override fun onClick(p0: View?) {
        when(p0){
            btnDonateForEducation, btnDonateFood, btnDonateClothes, btnDonateForKids, btnDonateForReligion, btnDonateOthers ->
                showDialog()
        }
    }

    private fun showDialog() {
        val dialog = Dialog(myView.context, R.style.custom_Dialog)
        val dialogView = LayoutInflater.from(myView.context).inflate(R.layout.dialog_donate_option, null)
        
        val tvGiveByYourself = dialogView.findViewById<TextView>(R.id.tvGiveByYourself)
        val tvUseOurService = dialogView.findViewById<TextView>(R.id.tvUseOurService)

        tvGiveByYourself.setOnClickListener {
            val intent = Intent(myView.context, PickupServiceActivity::class.java)
            startActivity(intent)
        }

        tvUseOurService.setOnClickListener {
            val intent = Intent(myView.context, PickupServiceActivity::class.java)
            startActivity(intent)
        }

        dialog.setContentView(dialogView)
        val window = dialog.window
        val wlp = window!!.attributes
        wlp.gravity = Gravity.CENTER
        window.attributes = wlp

        dialog.window!!.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.show()
    }
}