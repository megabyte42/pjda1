package com.lab1.basicactivity

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import java.text.SimpleDateFormat
import java.util.*

class InStoreSystem(context: Context) {

    var context : Context = context
    var drivers = mutableMapOf<String, String>()
    //var clockStatus = mutableMapOf("12345" to 0) //0 clocked out ; 1 clocked in
    var loginStatus = mutableMapOf<String, Boolean>()
    var driversRef = FirebaseFirestore.getInstance().collection("Drivers")
    var routesRef = FirebaseFirestore.getInstance().collection("Routes")

    var eventSeriesStatus = false

    var router: IRoute? = null


    //var ordersRef = FirebaseFirestore.getInstance().collection("orders")

    init {
        if (context is IRoute) {
            router = context
        } else {
            throw RuntimeException(context.toString() + " must implement IRoute") as Throwable
        }
        driversRef.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                Log.e(Constants.TAG, "Listen error $exception")
                return@addSnapshotListener
            }
            for (docChange in snapshot!!.documentChanges) {
                val d = Driver.fromSnapshot(docChange.document)
                when (docChange.type) {
                    DocumentChange.Type.ADDED -> {
                        //drivers.add(p!!)
                        drivers.put(d!!.employeeNum, d!!.password)

                        //notifyDataSetChanged()
                    }
                    DocumentChange.Type.REMOVED -> {
                        //drivers.remove(p)
                        drivers.remove(d!!.employeeNum)
                        //notifyDataSetChanged()
                    }
//                    DocumentChange.Type.MODIFIED -> {
//                        for ((pos, driver) in drivers) {
//                            if (d!!.id == driver!!.id) {
//                                edit(pos, d.employeeNum, d.password)
//                                notifyDataSetChanged()
//                                break
//                            }
//                        }
//                    }
                }
            }
        }
    }



    fun loginEmployee(employeeNumber: String, employeePassword: String) {
        Log.d(Constants.TAG, "trying to login $employeeNumber and $employeePassword")

        driversRef.get().addOnSuccessListener { snapshot: QuerySnapshot ->
            var ds = snapshot!!.toObjects(Driver::class.java)
            for (d in ds) {
                if (d.employeeNum == employeeNumber) {
                    Log.d(Constants.TAG, "an employee with that employee number exists")
                    if (d.password == employeePassword) {
                        Log.d(Constants.TAG, "passwords match")
                        loginStatus[employeeNumber] = true
                        router!!.sendLoginInfoBack(true)
                    }
                }
            }
        }
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
        routesRef.add(newOrder)
        router!!.onSendRoutingAssignment(newOrder)
    }



}