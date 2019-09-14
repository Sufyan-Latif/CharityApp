package com.androidkotlin.theta.android.charityapp.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.androidkotlin.theta.android.charityapp.R

/**
 * A simple [Fragment] subclass.
 */
class DonorHomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_donor_home, container, false)



        return view
    }


}
