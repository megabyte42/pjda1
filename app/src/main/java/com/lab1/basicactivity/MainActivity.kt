package com.lab1.basicactivity

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
//import android.support.design.widget.Snackbar
//import android.support.v7.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_login.view.*

class MainActivity : AppCompatActivity(),
    ButtonListener{


    private val auth = FirebaseAuth.getInstance()
    lateinit var authStateListener: FirebaseAuth.AuthStateListener
    private var currentEmployee = "00000"

    // Request code for launching the sign in Intent.
    private val RC_SIGN_IN = 1

    var switchTo: Fragment = SplashFragment()
    val inStoreSystem: InStoreSystem = InStoreSystem()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        if (inStoreSystem.loginStatus.get("11111")!!) {
            switchToFragment(HomeFragment())
        } else {
            switchToFragment(SplashFragment())
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
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

    private fun switchToFragment(fragment: Fragment) {
        switchTo = fragment
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_container, switchTo).addToBackStack("adding frag to backStack")
        ft.commit()
    }

    private fun showLoginButtonDialog() {
        val builder = AlertDialog.Builder(this)
        //Set options
        builder.setTitle(getString(R.string.dialog_login_title))

        // Content is message, view, or list of items
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_login, null, false)
        builder.setView(view)

        builder.setPositiveButton(android.R.string.ok) { _, _ ->
//            if (inStoreSystem.loginEmployee(view.dialog_login_employee_number_edit_text.toString(), view.dialog_login_employee_password_edit_text.toString())) {
//                switchToFragment(HomeFragment())
//            }
            switchToFragment(HomeFragment())
        }

        builder.setNegativeButton(android.R.string.cancel, null)
        builder.create().show()
    }

    override fun onBackPressed() {
        val ft = supportFragmentManager.beginTransaction()
        if (supportFragmentManager.backStackEntryCount > 0) {
            Log.d(Constants.TAG, "popping backstack")
            supportFragmentManager.popBackStackImmediate()
        }
        ft.commit()
    }

    // Interface methods
    override fun onLoginButtonPressed() {
        showLoginButtonDialog()
    }

    override fun onProfileButtonPressed() {
        switchToFragment(ProfileFragment())
    }

    override fun onSettingsButtonPressed() {
        switchToFragment(SettingsFragment())
    }

    override fun onRecentDeliveriesButtonPressed() {
        switchToFragment(RecentDeliveriesFragment())
    }

}
