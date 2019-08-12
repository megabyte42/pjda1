package com.lab1.basicactivity

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
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
                        router!!.setDriver(d)
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
        } else {
            eventSeriesStatus = false
        }
    }

    fun confirmOrder(bool: Boolean) {
        if (bool) {
            // TODO: cool db stuff of changing delivery status
            router!!.onOrderConfirmed()
        }
    }

    fun confirmArrived(bool: Boolean) {
        if (bool) {
            router!!.onArrived()
        }
    }

    private fun sendRoutingAssignment() {
        routesRef.orderBy(Route.ROUTE_KEY, Query.Direction.ASCENDING).get().addOnSuccessListener { snapshot: QuerySnapshot? ->
            Log.d(Constants.TAG, "got an orders by route thing")
            var rs = snapshot!!.toObjects(Route::class.java)
            var count: Int = 1
            for (r in rs) {
                router!!.onSendRoutingAssignment(r)
                count += 1
                if (count > 1) {
                    break
                }
            }
        }
    }
}