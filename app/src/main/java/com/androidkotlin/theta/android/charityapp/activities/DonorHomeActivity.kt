package com.androidkotlin.theta.android.charityapp.activities

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.support.v4.widget.DrawerLayout
import android.support.design.widget.NavigationView
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.androidkotlin.theta.android.charityapp.R
import com.androidkotlin.theta.android.charityapp.fragments.DonateFragment
import com.androidkotlin.theta.android.charityapp.fragments.DonateHistoryFragment
import com.androidkotlin.theta.android.charityapp.fragments.DonorHomeFragment
import com.androidkotlin.theta.android.charityapp.fragments.SetReminderFragment
import com.androidkotlin.theta.android.charityapp.utils.SharedPrefs

class DonorHomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var sharedPrefs: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donor_home)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        title = resources.getString(R.string.app_name)

        sharedPrefs = SharedPrefs.getSharedPrefs(this)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val headerView: View = navView.getHeaderView(0)
        val tvName: TextView = headerView.findViewById(R.id.tvName)
        val tvUsername: TextView = headerView.findViewById(R.id.tvUsername)

        val name: String? = sharedPrefs?.getString("first_name", "") + " " + sharedPrefs?.getString("last_name", "")
        tvName.text = name
        tvUsername.text = sharedPrefs?.getString("username", "")

        navView.setNavigationItemSelectedListener(this)

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.donor_home_container, DonorHomeFragment())
        fragmentTransaction.commit()
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.donor_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        when (item.itemId) {
            R.id.nav_home -> {
                // Handle the camera action
                fragmentTransaction.replace(R.id.donor_home_container, DonorHomeFragment())
                fragmentTransaction.commit()
            }
            R.id.nav_organization_activities -> {

            }
            R.id.nav_donate -> {
                fragmentTransaction.replace(R.id.donor_home_container, DonateFragment())
                fragmentTransaction.commit()

//                fab.visibility = View.GONE
            }
            R.id.nav_setReminder -> {
                Toast.makeText(this, "Long click on item to make changes", Toast.LENGTH_SHORT).show()
                fragmentTransaction.replace(R.id.donor_home_container, SetReminderFragment()).commit()
            }
            R.id.nav_viewHistory -> {
                fragmentTransaction.replace(R.id.donor_home_container, DonateHistoryFragment()).commit()
            }
            R.id.nav_edit_profile -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
            R.id.nav_logout -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Alert!")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton("YES") { _, _ ->
                            run {
                                val editor = sharedPrefs?.edit()
                                editor?.clear()
                                editor?.apply()
                                val intent = Intent(this, LoginActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        }
                        .setNegativeButton("NO") { _, _ -> }
                        .create()
                        .show()
            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
