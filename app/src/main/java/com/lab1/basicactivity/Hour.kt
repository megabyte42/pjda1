package com.lab1.basicactivity

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.Parcelize

@Parcelize
@SuppressLint("ParcelCreator")
data class Hour(var employeeNum: String = "", var count: String = "", var type: String = "") : Parcelable {

    @get:Exclude
    var id = ""
    @ServerTimestamp var timeIn: Timestamp? = null

    companion object {
        const val HOUR_KEY = "timeIn"
        fun fromSnapshot(snapshot: DocumentSnapshot) : Hour? {
            val h = snapshot.toObject(Hour::class.java)
            h?.id = snapshot.id
            return h
        }
    }


}