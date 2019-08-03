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
            0 -> byDay(fragment)
            1 -> byWeek(fragment)
            2 -> byMonth(fragment)
            else -> null
        }
    }

    private fun byDay(fragment: ProfileFragment) {
        val now = LocalDateTime.now(ZoneOffset.UTC)
        val seconds = now.atZone(ZoneOffset.UTC).toEpochSecond()
        val fmt = SimpleDateFormat("yyyyMMdd")
        hoursRef.whereEqualTo("employeeNum", employeeNum).orderBy(Hour.HOUR_KEY, Query.Direction.ASCENDING).get().addOnSuccessListener { snapshot: QuerySnapshot? ->
            var hs = snapshot!!.toObjects(Hour::class.java)
            for (h in hs) {
                Log.d(Constants.TAG, "timeIn: ${h.timeIn!!.seconds} now: $seconds")
                // Hours Scheduled
                Log.d(Constants.TAG, "timeIn simple: ${fmt.format(h.timeIn!!.toDate())} current simple: ${fmt.format(Timestamp(seconds, 0).toDate())}")
                if (fmt.format(h.timeIn!!.toDate()).equals(fmt.format(Timestamp(seconds, 0).toDate()))) {
                    Log.d(Constants.TAG, "found timeIn on same day as today")
                    if (h.type == "scheduled") {
                        fragment.updateHoursScheduled(h.count)
                    }
                }
                // Hours Worked
            }
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

    private fun byWeek(fragment: ProfileFragment) {
        // TODO
    }

    private fun byMonth(fragment: ProfileFragment) {
        val now = LocalDateTime.now(ZoneOffset.UTC)
        val seconds = now.atZone(ZoneOffset.UTC).toEpochSecond()
        val fmt = SimpleDateFormat("yyyyMM")
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