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
        DateTimeFormatter.ISO_INSTANT.format(Instant.now())
        hoursRef.whereEqualTo("employeeNum", employeeNum).orderBy(Hour.HOUR_KEY, Query.Direction.ASCENDING).get().addOnSuccessListener { snapshot: QuerySnapshot? ->
            var hs = snapshot!!.toObjects(Hour::class.java)
            for (h in hs) {
                val now = LocalDateTime.now(ZoneOffset.UTC)
                val seconds = now.atZone(ZoneOffset.UTC).toEpochSecond()
                Log.d(Constants.TAG, "timeIn: ${h.timeIn!!.seconds} now: $seconds")
                if ((h.timeIn!!.seconds > seconds)) {
                    // these timestamps are for times that haven't happened yet, likely future scheduled hours
                    } else {
                    Log.d(Constants.TAG, "timeIn occurred before current time")
                    // Hours Scheduled
                    val fmt = SimpleDateFormat("yyyyMMdd")
                    //if (h.timeIn is Timestamp) {
                    Log.d(Constants.TAG, "timeIn simple: ${fmt.format(h.timeIn!!.toDate())} current simple: ${fmt.format(Timestamp(seconds, 0).toDate())}")
                        //if (fmt.format(h.timeIn!!.toDate()).equals(fmt.format(seconds))) {
                        //if ()
                            Log.d(Constants.TAG, "found timeIn on same day as today")
                            if (h.type == "scheduled") {
                                fragment.updateHoursScheduled(h.count)
                            }
                        //}
                    //}

                    // Hours Worked
                }
            }
//            if (hs.contains(pics[position])) {
//                picsRef.document(pics[position].id).delete()
//                Log.d(Constants.TAG, "allowed to delete so delete")
//                notifyDataSetChanged()
//            } else {
//                var t = Toast.makeText(context, "You do not have permission to delete this item", Toast.LENGTH_LONG)
//                t.show()
//                notifyDataSetChanged()
//            }
        }
    }

    private fun byWeek(fragment: ProfileFragment) {

    }

    private fun byMonth(fragment: ProfileFragment) {

    }



}