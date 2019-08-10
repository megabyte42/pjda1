package com.lab1.basicactivity

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firestore.v1.DocumentTransform
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class ProfileAdapter(context: Context, employeeNum: String) {

    private var driversRef = FirebaseFirestore.getInstance().collection("Drivers")
    private var hoursRef = FirebaseFirestore.getInstance().collection("Hours")
    private var tipsRef = FirebaseFirestore.getInstance().collection("Tips")
    private var employeeNum = employeeNum

    fun updateTable(position: Int, fragment: ProfileFragment) {
        when (position) {
            0 -> {  val fmt = SimpleDateFormat("yyyyMMdd")
                    update(fragment, fmt)}
            1 -> {  val fmt = SimpleDateFormat("ww")
                    update(fragment, fmt)}
            2 -> {  val fmt = SimpleDateFormat("yyyyMM")
                    update(fragment, fmt)}
            else -> null
        }
    }

    fun updateProfileInfo(fragment: ProfileFragment) {
        driversRef.whereEqualTo("employeeNum", employeeNum).get().addOnSuccessListener { snapshot: QuerySnapshot? ->
            var ds = snapshot!!.toObjects(Driver::class.java)
            for (d in ds) {
                fragment.updateName(d.name)
                fragment.updateNumber(d.employeeNum)
                val fmt = SimpleDateFormat("hh:mm")

                Log.d(Constants.TAG, "time is  ${fmt.format(d.timeIO!!.toDate())}")
                fragment.updateClockStatus(d.clockStatus, fmt.format(d.timeIO!!.toDate()))
                Log.d(Constants.TAG, "d.id is ${d.id}")
                var database = FirebaseFirestore.getInstance()
                var oppositeOfCurrentStat: Boolean = !d.clockStatus
                database.collection("Drivers").document("ioi9B6SIjvyA8oCkaNgn").update("timeIO", Timestamp.now())
                database.collection("Drivers").document("ioi9B6SIjvyA8oCkaNgn").update("clockStatus", oppositeOfCurrentStat)
            }

        }
    }


    private fun update(fragment: ProfileFragment, fmt: SimpleDateFormat) {
        val now = LocalDateTime.now(ZoneOffset.UTC)
        val seconds = now.atZone(ZoneOffset.UTC).toEpochSecond()


        hoursRef.whereEqualTo("employeeNum", employeeNum).orderBy(Hour.HOUR_KEY, Query.Direction.ASCENDING).get().addOnSuccessListener { snapshot: QuerySnapshot? ->
            var hs = snapshot!!.toObjects(Hour::class.java)
            var hoursTotal: Int = 0
            for (h in hs) {
                Log.d(Constants.TAG, "timeIn: ${h.timeIn!!.seconds} now: $seconds")
                // Hours Scheduled
                Log.d(Constants.TAG, "timeIn simple: ${fmt.format(h.timeIn!!.toDate())} current simple: ${fmt.format(Timestamp(seconds, 0).toDate())}")
                if (fmt.format(h.timeIn!!.toDate()).equals(fmt.format(Timestamp(seconds, 0).toDate()))) {
                    Log.d(Constants.TAG, "found timeIn on same day as today")
                    if (h.type == "scheduled") {
                        hoursTotal = hoursTotal + h.count.toInt()
                    }
                }
                // Hours Worked
            }
            fragment.updateHoursScheduled(hoursTotal.toString())
        }
        tipsRef.whereEqualTo("employeeNum", employeeNum).get().addOnSuccessListener { snapshot: QuerySnapshot ->
            var ts = snapshot!!.toObjects(Tip::class.java)
            var tipTotal: Int = 0 // TODO: support for decimal
            for (t in ts) {
                if (fmt.format(t.time!!.toDate()).equals(fmt.format(Timestamp(seconds, 0).toDate()))) {
                    //Log.d(Constants.TAG, "found timeIn on same day as today")
                    tipTotal += t.amount.toInt()
                }
            }
            fragment.updateTipTotal(tipTotal.toString())
        }
    }
}