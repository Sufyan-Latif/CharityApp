package com.androidkotlin.theta.android.charityapp.fragments


import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.androidkotlin.theta.android.charityapp.R
import com.androidkotlin.theta.android.charityapp.adapters.ReminderAdapter
import com.androidkotlin.theta.android.charityapp.adapters.ReminderAdapter.OnReminderLongClick
import com.androidkotlin.theta.android.charityapp.databases.CharityDatabase
import com.androidkotlin.theta.android.charityapp.databases.Reminder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_set_reminder.*

/**
 * A simple [Fragment] subclass.
 */
class SetReminderFragment : Fragment() {

    private var myView: View? = null

    private var fabAddReminder: FloatingActionButton? = null
    private var rvReminder: RecyclerView? = null
    private var mremindersList: List<Reminder>? = ArrayList()


    companion object{
        private var remindersList: ArrayList<Reminder>? = ArrayList()
        private var mReminderAdapter: ReminderAdapter? = null

        private fun updateAdapter() {
            mReminderAdapter!!.setReminderList(remindersList)
        }

        fun setRemindersList(remindersList: ArrayList<Reminder>){
            this.remindersList = remindersList
            updateAdapter()

        }
        fun getRemindersList() : ArrayList<Reminder>?{
            return remindersList
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_set_reminder, container, false)

        bindViews()

        /*val reminder = Reminder(time = "2334", instructions = "sdfsd", amount = 312)
        remindersList?.add(reminder)*/

//        updateAdapter()

        class GetListInBackground : AsyncTask<Void, Void, Void>(){

            private var db: CharityDatabase? = null

            override fun onPreExecute() {
                super.onPreExecute()
                db = CharityDatabase.getCharityDatabase(myView!!.context)
            }

            override fun doInBackground(vararg p0: Void?): Void? {
                remindersList = db?.reminderDao()?.getAll() as ArrayList<Reminder>
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                updateAdapter()

            }
        }

        GetListInBackground().execute()
        /*class getList() : AsyncTask<Void, Void, Void>() {
            private var db: CharityDatabase? = null
            override fun onPreExecute() {
                super.onPreExecute()
                db = CharityDatabase.getCharityDatabase(myView!!.context)
            }

            override fun doInBackground(vararg p0: Void?): Void? {
                remindersList = db?.reminderDao()?.getAll() as ArrayList<Reminder>
                return null
            }

            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
                updateAdapter()
            }

        }
        AsyncTask.execute {
            val db = CharityDatabase.getCharityDatabase(myView!!.context)
//            val list: List<Reminder>? = db?.reminderDao()?.getAll()
            remindersList = db?.reminderDao()?.getAll() as ArrayList<Reminder>

            Log.d("No. of reminders", remindersList?.size.toString())

            updateAdapter()
        }
*/
        fabAddReminder?.setOnClickListener {
            val fragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.donor_home_container, AddReminderFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        return myView
    }



    private fun bindViews() {
        mReminderAdapter = ReminderAdapter(myView!!.context, object : ReminderAdapter.OnReminderLongClick {
            override fun onLongClick(reminder: Reminder, popupOption: Int) {

                class DeleteReminderInBackground() : AsyncTask<Void, Void, Void>(){

                    private var db: CharityDatabase? = null

                    override fun onPreExecute() {
                        super.onPreExecute()
                        db = CharityDatabase.getCharityDatabase(myView!!.context)
                    }

                    override fun doInBackground(vararg p0: Void?): Void? {
                        db?.reminderDao()?.deleteReminder(reminder)
                        remindersList?.remove(reminder)
                        return null
                    }

                    override fun onPostExecute(result: Void?) {
                        super.onPostExecute(result)
                        updateAdapter()
                        Toast.makeText(myView?.context, "Deleted Successfully", Toast.LENGTH_SHORT).show()
                    }
                }

                if (popupOption == 1)
                    DeleteReminderInBackground().execute()
                else if (popupOption == 0){
                    val fragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
                    val newFragment = AddReminderFragment()
                    val args = Bundle()
                    args.putSerializable("reminder", reminder)
                    fragmentTransaction.replace(R.id.donor_home_container, newFragment)
                    fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.commit()
                }
            }
        })
        rvReminder = myView!!.findViewById(R.id.rvReminder)
        val manager = LinearLayoutManager(myView!!.context)
        rvReminder?.layoutManager = manager
        rvReminder?.adapter = mReminderAdapter

        fabAddReminder = myView?.findViewById(R.id.fabAddReminder)
    }
}
