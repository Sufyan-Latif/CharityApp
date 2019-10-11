package com.androidkotlin.theta.android.charityapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.androidkotlin.theta.android.charityapp.R
import com.androidkotlin.theta.android.charityapp.models.Donor
import kotlinx.android.synthetic.main.donor_list_item.view.*

class DonorsAdapter(val context: Context) : RecyclerView.Adapter<DonorsAdapter.DonorsViewHolder>() {

    private var donorsList: ArrayList<Donor> = arrayListOf()

    override fun onCreateViewHolder(p0: ViewGroup, position: Int): DonorsViewHolder {
        val view : View = LayoutInflater.from(context).inflate(R.layout.donor_list_item, p0, false)
        return DonorsViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: DonorsViewHolder, position: Int) {
        val donor = donorsList[position]
        donor.firstName = viewHolder.itemView.tvName.toString()

    }

    override fun getItemCount(): Int {
        return donorsList.size
    }

    class DonorsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}