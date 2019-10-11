package com.androidkotlin.theta.android.charityapp.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

import com.androidkotlin.theta.android.charityapp.R
import com.androidkotlin.theta.android.charityapp.utils.SharedPrefs
import kotlinx.android.synthetic.main.fragment_donor_home.*

/**
 * A simple [Fragment] subclass.
 */
class DonorHomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val myView = inflater.inflate(R.layout.fragment_donor_home, container, false)

        val tvName: TextView = myView.findViewById(R.id.tvName)
        val btnDonateNow: Button = myView.findViewById(R.id.btnDonateNow)
        val btnViewRecord: Button = myView.findViewById(R.id.btnViewRecord)

        val sharedPrefs = SharedPrefs.getSharedPrefs(myView.context)
        val name: String? = sharedPrefs?.getString("first_name", "") + " " + sharedPrefs?.getString("last_name", "")
        tvName.text = name

        val fragmentTransaction = activity!!.supportFragmentManager.beginTransaction()

        btnDonateNow.setOnClickListener {
            fragmentTransaction.replace(R.id.donor_home_container, DonateFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        btnViewRecord.setOnClickListener {
            fragmentTransaction.replace(R.id.donor_home_container, DonateHistoryFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        return myView
    }
}
