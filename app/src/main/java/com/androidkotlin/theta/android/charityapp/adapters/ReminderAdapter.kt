package com.androidkotlin.theta.android.charityapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.androidkotlin.theta.android.charityapp.R
import com.androidkotlin.theta.android.charityapp.databases.Reminder
import kotlinx.android.synthetic.main.reminder_list_item.view.*

class ReminderAdapter(val context: Context, onReminderLongClickListener: OnReminderLongClick) : RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder>() {

    private var remindersList: ArrayList<Reminder> = arrayListOf()
    private var onReminderClick: OnReminderLongClick? = onReminderLongClickListener

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ReminderViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.reminder_list_item, p0, false)
        return ReminderViewHolder(view)
    }

    override fun getItemCount(): Int {
        return remindersList.size
    }

    override fun onBindViewHolder(p0: ReminderViewHolder, p1: Int) {
        val reminder = remindersList[p1]

        p0.tvTime.text = reminder.time
        p0.tvInstructions.text = reminder.instructions
        p0.tvAmount.text = reminder.amount.toString()

        p0.itemView.setOnLongClickListener {
            val popupMenu: PopupMenu = PopupMenu(context, p0.itemView)
            popupMenu.menuInflater.inflate(R.menu.reminder_more_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                if (it.title.equals("Update"))
                    onReminderClick?.onLongClick(reminder, 0)
                else if (it.title.equals("Delete"))
                    onReminderClick?.onLongClick(reminder, 1)
                Toast.makeText(context, "Menu Clicked", Toast.LENGTH_SHORT).show()
                true
            }
            popupMenu.show()
            true
        }
    }

    fun setReminderList(remindersList: ArrayList<Reminder>?) {
        this.remindersList = remindersList!!
        notifyDataSetChanged()
    }

    class ReminderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvTime: TextView = itemView.tvTime
        val tvAmPm: TextView = itemView.tvAmPm
        val tvInstructions: TextView = itemView.tvInstructions
        val tvAmount: TextView = itemView.tvAmount
    }

    interface OnReminderLongClick{
        fun onLongClick(reminder: Reminder, popupOption: Int)
    }
}