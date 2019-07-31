package com.lab1.basicactivity

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class InStoreSystem(context: Context) {

   // var context : Context = context
    var employees = mutableMapOf("12345" to "password")
    var clockStatus = mutableMapOf("12345" to 0) //0 clocked out ; 1 clocked in
    var loginStatus = mutableMapOf("12345" to true, "11111" to false)
    var eventSeriesStatus = false

    var router: IRoute? = null

    var ordersRef = FirebaseFirestore.getInstance().collection("orders")

    init {
        if (context is IRoute) {
            router = context
        } else {
            throw RuntimeException(context.toString() + " must implement IRoute") as Throwable
        }
    }



    fun loginEmployee(employeeNumber: String, employeePassword: String): Boolean {
        if (employees.containsKey(employeeNumber)) {
            if (employees[employeeNumber] == (employeePassword)) {
                loginStatus[employeeNumber] = true
                Log.d(Constants.TAG, "employee logged in")
                return true
            }
        }
        return false
    }

    fun triggerNextEventSeries() {
        eventSeriesStatus = true
        sendRoutingAssignment()
    }

    fun acceptRoute(bool: Boolean) {
        if (bool) {
            // TODO: cool db stuff of changing delivery status
            router!!.onRouteAccepted()
        }
    }

    fun confirmOrder(bool: Boolean) {
        if (bool) {
            // TODO: cool db stuff of changing delivery status
            router!!.onOrderConfirmed()
        }
    }

    private fun sendRoutingAssignment() {
        var newOrder = Order("Order Name",
                            "1212 Test Ln, Testville, TN, 12121",
                            "1231231212",
                            SimpleDateFormat("yyyyMMdd_HHmmss").format(Date()),
                            "14 +Cheese") //TODO: get random newOrder
        ordersRef.add(newOrder)
        router!!.onSendRoutingAssignment(newOrder)
    }



}