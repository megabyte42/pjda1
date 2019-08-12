package com.lab1.basicactivity

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_accept_route.view.*
import kotlinx.android.synthetic.main.dialog_confirm_order.view.*
import kotlinx.android.synthetic.main.dialog_login.view.*
import android.content.Intent
import android.net.Uri
import kotlinx.android.synthetic.main.dialog_accept_route.view.amount_text
import kotlinx.android.synthetic.main.dialog_call_customer.view.*
import kotlinx.android.synthetic.main.dialog_confirm_arrived.view.*
import kotlinx.android.synthetic.main.dialog_log_tip_or_return_to_store.view.*

var colorBlindMode: Boolean = false

class MainActivity : AppCompatActivity(),
    ButtonListener, IRoute {



    var currentRoute: Route? = null
    var currentDriver: Driver? = null
    var wentToMaps: Boolean = false

    var switchTo: Fragment = SplashFragment()
    val inStoreSystem: InStoreSystem = InStoreSystem(this)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val prefs = getSharedPreferences(PrefHelper.PREFS, Context.MODE_PRIVATE)
        wentToMaps = prefs.getBoolean(PrefHelper.KEY_WENT_TO_MAPS, false)

        if (wentToMaps) {
            Log.d(Constants.TAG, "already went to maps, so launch Home Fragment")
            switchToFragment(HomeFragment())
        }
        switchToFragment(SplashFragment())

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
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
        builder.setTitle(getString(R.string.dialog_login_title))
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_login, null, false)
        builder.setView(view)

        builder.setPositiveButton(android.R.string.ok) { _, _ ->
            inStoreSystem.loginEmployee(view.dialog_login_employee_number_edit_text.text.toString(), view.dialog_login_employee_password_edit_text.text.toString())
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

    private fun showDialogAcceptRoute() {
        Log.d(Constants.TAG, "show dialog accept route")
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.accept_route))

        var view = LayoutInflater.from(this).inflate(R.layout.dialog_accept_route, null, false)
        if (colorBlindMode) {
            view = LayoutInflater.from(this).inflate(R.layout.dialog_accept_route_0000, null, false)
        }
        view.address_text.text = currentRoute!!.address
        view.items_text.text = currentRoute!!.desc
        view.amount_text.text = "$" +  currentRoute!!.amount
        builder.setView(view)

        builder.setPositiveButton(android.R.string.ok, DialogInterface.OnClickListener { dialog, id ->
            inStoreSystem.acceptRoute(true)
            dialog.cancel()
        })
        builder.setNegativeButton(android.R.string.cancel, DialogInterface.OnClickListener {dialog, id ->
            inStoreSystem.acceptRoute(false)
            dialog.cancel()
        })
        builder.create().show()
    }

    override fun onRouteAccepted() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirm Order")
        var view = LayoutInflater.from(this).inflate(R.layout.dialog_confirm_order, null, false)
        if (colorBlindMode) {
            view = LayoutInflater.from(this).inflate(R.layout.dialog_confirm_order_0000, null, false)
        }
        view.order_id_text.text = currentRoute!!.orderNum
        view.dialog_confirm_order_items_text.text = currentRoute!!.desc
        builder.setView(view)

        builder.setPositiveButton(android.R.string.ok) { _, _ ->
            inStoreSystem.confirmOrder(true)
        }
        builder.setNegativeButton(android.R.string.cancel) {_, _ ->
            inStoreSystem.confirmOrder(false)
        }
        builder.create().show()
    }

    private fun showConfirmArrivedDialog() {
        Log.d(Constants.TAG, "show confirm arrived dialog")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Arrived")
        var view = LayoutInflater.from(this).inflate(R.layout.dialog_confirm_arrived, null, false)
        if (colorBlindMode) {
            view = LayoutInflater.from(this).inflate(R.layout.dialog_confirm_arrived_0000, null, false)
        }
        builder.setView(view)

        view.amount_text.text = "$" + currentRoute!!.amount
        view.instructions_text.text = currentRoute!!.instructions

        builder.setPositiveButton(android.R.string.ok) { _, _ ->
            inStoreSystem.confirmArrived(true)
        }
        builder.setNegativeButton(android.R.string.cancel) {_, _ ->
            inStoreSystem.confirmArrived(false)
        }
        builder.create().show()
    }

    private fun showReturnOrTipDialog() {
        Log.d(Constants.TAG, "show return or log tip dialog")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Log Tip or Return to Store")
        var view = LayoutInflater.from(this).inflate(R.layout.dialog_log_tip_or_return_to_store, null, false)
        if (colorBlindMode) {
            view = LayoutInflater.from(this).inflate(R.layout.dialog_log_tip_or_return_to_store_0000, null, false)
        }
        builder.setView(view)

        builder.setPositiveButton(android.R.string.ok) {_, _ ->
            currentRoute!!.tip = view.dialog_log_tip_amount.text.toString()
            wentToMaps = false
            val gmmIntentUri = Uri.parse(getString(R.string.STORE_ADDRESS))
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
        builder.setNegativeButton(android.R.string.cancel) {_, _ ->
            wentToMaps = false
            val gmmIntentUri = Uri.parse(getString(R.string.STORE_ADDRESS))
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }

        builder.create().show()
    }

    // Interface methods
    override fun onSendRoutingAssignment(route: Route) {
        currentRoute = route
        showDialogAcceptRoute()
    }



    override fun onOrderConfirmed() {
        wentToMaps = true
        inStoreSystem.eventSeriesStatus = false

        val gmmIntentUri = Uri.parse("geo:39.4667,87.4139?q=${currentRoute!!.address}")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }

    override fun onArrived() {
        showReturnOrTipDialog()
    }

    override fun onLoginButtonPressed() {
        showLoginButtonDialog()
    }

    override fun onProfileButtonPressed() {
        switchToFragment(ProfileFragment(currentDriver!!.employeeNum))
    }

    override fun onSettingsButtonPressed() {
        switchToFragment(SettingsFragment())
    }

    override fun onRecentDeliveriesButtonPressed() {
        switchToFragment(RecentDeliveriesFragment())
    }

    override fun toggleColorBlindMode(checked: Boolean) {
        Log.d(Constants.TAG, "setting colorBlindMode to $checked")
        colorBlindMode = checked
        switchToFragment(SettingsFragment())
    }

    override fun onTriggerSoftware() {
        if (wentToMaps) {
            Log.d(Constants.TAG, "already went to maps, launch confirm arrived dialog")
            showConfirmArrivedDialog()
        } else if (!inStoreSystem.eventSeriesStatus){
            Log.d(Constants.TAG, "starting an event series")
            inStoreSystem.triggerNextEventSeries()
        } else {
            Log.d(Constants.TAG, "idk what else to do")
        }
    }



    override fun onPhonePresesd(phone: String) {
        Log.d(Constants.TAG, "onPhonePressed with phone number as: $phone")
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.accept_route))

        val view = LayoutInflater.from(this).inflate(R.layout.dialog_call_customer, null, false)
        builder.setView(view)

        view.dialog_call_customer_call_customer_button.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone))
            startActivity(intent)
        }
        builder.setNegativeButton(android.R.string.cancel, null)
        builder.create().show()

    }

    override fun setDriver(d: Driver) {
        currentDriver = d
    }

    override fun sendLoginInfoBack(status: Boolean) {
        switchToFragment(HomeFragment())
    }

    override fun onPause() {
        super.onPause()
        val prefs = getSharedPreferences(PrefHelper.PREFS, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(PrefHelper.KEY_ORDER_ADDRESS, currentRoute!!.address)
        editor.putString(PrefHelper.KEY_ORDER_AMOUNT, currentRoute!!.amount)
        editor.putString(PrefHelper.KEY_ORDER_DESC, currentRoute!!.desc)
        editor.putString(PrefHelper.KEY_ORDER_INSTRUCTIONS, currentRoute!!.instructions)
        editor.putString(PrefHelper.KEY_ORDER_NAME, currentRoute!!.name)
        editor.putString(PrefHelper.KEY_ORDER_NUMBER, currentRoute!!.orderNum)
        editor.putString(PrefHelper.KEY_ORDER_PAYMENT_METHOD, currentRoute!!.paymentMethod)
        editor.putString(PrefHelper.KEY_ORDER_PHONE, currentRoute!!.phone)
        //editor.(PrefHelper.KEY_ORDER_TIME, TimeStampObject(currentOrder!!.time))
        editor.putString(PrefHelper.KEY_ORDER_TIP, currentRoute!!.tip)

        editor.putBoolean(PrefHelper.KEY_WENT_TO_MAPS, wentToMaps!!)

        editor.commit()
    }

}
