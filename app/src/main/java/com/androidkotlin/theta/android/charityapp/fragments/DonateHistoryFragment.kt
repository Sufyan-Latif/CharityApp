package com.androidkotlin.theta.android.charityapp.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.androidkotlin.theta.android.charityapp.R

/**
 * A simple [Fragment] subclass.
 */
class DonateHistoryFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_donate_history, container, false)
    }


}
