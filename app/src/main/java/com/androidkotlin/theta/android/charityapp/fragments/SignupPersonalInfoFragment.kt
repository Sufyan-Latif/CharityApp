package com.androidkotlin.theta.android.charityapp.fragments


import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import com.androidkotlin.theta.android.charityapp.R
import com.androidkotlin.theta.android.charityapp.models.Acceptor
import com.androidkotlin.theta.android.charityapp.models.Donor
import com.androidkotlin.theta.android.charityapp.utils.Constant
import kotlinx.android.synthetic.main.fragment_donor_signup_one.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 *
 */
class SignupPersonalInfoFragment : Fragment() {

    private lateinit var myView: View
    private var firstName:String? = ""
    private var lastName:String? = ""
    private var contactNum:String? = ""
    private var address:String? = ""
    private var dateOfBirth:String? = ""
    private var gender:String? = ""
    private lateinit var donor: Donor
    private lateinit var acceptor: Acceptor

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        myView = inflater.inflate(R.layout.fragment_donor_signup_one, container, false)

        myView.findViewById<TextView>(R.id.tv_dob).setOnClickListener { showCalender() }

        val back: Button = myView.findViewById(R.id.btn_donor_back)
        back.setOnClickListener {
            activity!!.finish()
        }
        val next: Button = myView.findViewById(R.id.btn_donor_next)
        next.setOnClickListener {
//            validateData()
            if (validateFirstName() && validateLastName() && validateContactNum() && validateAddress()
                    && validateDOB() && validateGender()){
                val bundle = Bundle()

                when(Constant.signUpModel) {

                    "acceptor" -> {
                        acceptor= Acceptor(firstName= firstName, lastName = lastName, contactNum = contactNum,
                                address = address, dateOfBirth = dateOfBirth, gender = gender)
                        bundle.putSerializable("acceptor", acceptor)
                    }
                    "donor" -> {
                        donor= Donor(firstName= firstName, lastName = lastName, contactNum = contactNum,
                                address = address, dateOfBirth = dateOfBirth, gender = gender)
                        bundle.putSerializable("donor", donor)
                    }
                    "organization" -> {
                        //TODO: Initialize organization object
                    }
                }

                val fragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
                val accountInfoFragment = AccountInfoFragment()
                accountInfoFragment.arguments = bundle
                fragmentTransaction.replace(R.id.signup_container, accountInfoFragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        }

        // Inflate the layout for this fragment
        return myView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        gender_group.setOnCheckedChangeListener { _, checkedId ->
            val genderButton: RadioButton = myView.findViewById(checkedId)
            gender = genderButton.text.toString()
        }
    }

    private fun showCalender(){
        val cal = Calendar.getInstance()
        val year: Int = cal.get(Calendar.YEAR)
        val month: Int = cal.get(Calendar.MONTH)
        val day: Int = cal.get(Calendar.DAY_OF_MONTH)

        val dialog= DatePickerDialog(
                myView.context,
//                activity!!.applicationContext,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
//                android.R.style.Widget_PopupWindow,
                DatePickerDialog.OnDateSetListener { _, year, month, day ->
                    tv_dob.text = "$day/${month+1}/$year"
                },
                year, month, day
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

    private fun validateFirstName(): Boolean {
        firstName = et_donor_first_name.text.toString().trim()

        return if (firstName == ""){
            et_donor_first_name.requestFocus()
            et_donor_first_name.error = "Enter First Name"
            false
        }
        else{
            et_donor_first_name.error = null
            true
        }
    }

    private fun validateLastName(): Boolean {
        lastName = et_donor_last_name.text.toString().trim()

        return if (lastName == ""){
            et_donor_last_name.requestFocus()
            et_donor_last_name.error = "Enter Last Name"
            false
        }
        else{
            et_donor_last_name.error = null
            true
        }
    }

    private fun validateContactNum(): Boolean {
        contactNum = et_donor_contact.text.toString().trim()

        return if (contactNum == ""){
            et_donor_contact.requestFocus()
            et_donor_contact.error = "Enter Contact Number"
            false
        }
        else{
            et_donor_contact.error = null
            true
        }
    }

    private fun validateAddress(): Boolean {
        address = et_donor_address.text.toString().trim()

        return if (address == ""){
            et_donor_address.requestFocus()
            et_donor_address.error = "Enter Your Address"
            false
        }
        else{
            et_donor_address.error = null
            true
        }
    }

    private fun validateDOB(): Boolean {
        dateOfBirth = tv_dob.text.toString()

        return if (dateOfBirth == "DD/MM/YYYY"){
            Toast.makeText(myView.context, "Enter Date of birth", Toast.LENGTH_SHORT).show()
            false
        }
        else
            true
    }
    private fun validateGender(): Boolean {

        return if (gender == ""){
            Toast.makeText(activity, "Choose Gender", Toast.LENGTH_SHORT).show()
            false
        }
        else{
            true
        }
    }
}