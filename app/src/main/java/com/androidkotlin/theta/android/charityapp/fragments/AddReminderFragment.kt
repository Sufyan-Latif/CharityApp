package com.androidkotlin.theta.android.charityapp.fragments


import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment

import com.androidkotlin.theta.android.charityapp.R
import com.androidkotlin.theta.android.charityapp.databases.CharityDatabase
import com.androidkotlin.theta.android.charityapp.databases.Reminder

/**
 * A simple [Fragment] subclass.
 */
class AddReminderFragment : Fragment() {

    private lateinit var myView: View
    private lateinit var tpReminder: TimePicker
    private lateinit var btnOk: Button
    private lateinit var btnCancel: Button
    private lateinit var etInstructions: EditText
    private lateinit var etAmount: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_add_reminder, container, false)

        bindViews()

        var reminder: Reminder? = null
        if (arguments != null) {
            reminder = arguments?.getSerializable("reminder") as Reminder
        }

        btnOk.setOnClickListener {
            val hours = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tpReminder.hour
            } else {
                tpReminder.currentHour
            }
            val minutes = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tpReminder.minute
            } else {
                tpReminder.currentMinute
            }
            val ampm: String =
                    if (hours < 12)
                        "am"
                    else
                        "pm"
            val time = "$hours:$minutes $ampm"
            val instructions: String = etInstructions.text.toString()
            var amount: Int? = null
            if (etAmount.text.toString() != "")
                amount = etAmount.text.toString().toInt()
            if (reminder != null) {
                updateReminder(reminder)
            }

            addNewReminder(time = time, instructions = instructions, amount = amount)
            Toast.makeText(myView.context, "Reminder saved", Toast.LENGTH_SHORT).show()
        }

        btnCancel.setOnClickListener {
            activity!!.onBackPressed()
        }

        return myView
    }

    private fun bindViews() {
        btnOk = myView.findViewById(R.id.btnOk)
        btnCancel = myView.findViewById(R.id.btnCancel)
        etInstructions = myView.findViewById(R.id.etInstructions)
        etAmount = myView.findViewById(R.id.etAmount)
        tpReminder = myView.findViewById(R.id.tpReminder)
    }

    private fun addNewReminder(time: String, instructions: String, amount: Int?) {
        AsyncTask.execute {
            val db = CharityDatabase.getCharityDatabase(myView.context)
            db?.reminderDao()?.addReminder(Reminder(time = time, instructions = instructions, amount = amount))
//            activity!!.onBackPressed()
        }
    }

    private fun updateReminder(reminder: Reminder) {

        class UpdateReminderInBackground() : AsyncTask<Void, Void, Void>() {

            private var db: CharityDatabase? = null

            override fun onPreExecute() {
                super.onPreExecute()
                db = CharityDatabase.getCharityDatabase(myView.context)
            }

            override fun doInBackground(vararg p0: Void?): Void? {
                db?.reminderDao()?.updateReminder(reminder)
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                val remindersList: ArrayList<Reminder>? = SetReminderFragment.getRemindersList()
                remindersList?.remove(reminder)
                val newReminder = Reminder(time = reminder.time, instructions = reminder.instructions, amount = reminder.amount)
                remindersList?.add(newReminder)
                SetReminderFragment.setRemindersList(remindersList!!)
            }
        }
        UpdateReminderInBackground().execute()
    }
}
