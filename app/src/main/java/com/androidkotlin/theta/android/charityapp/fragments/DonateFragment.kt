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
class DonateFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view:View =  inflater.inflate(R.layout.fragment_donate, container, false)

        return view
    }


}
