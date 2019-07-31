package com.lab1.basicactivity

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import kotlinx.android.parcel.Parcelize
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.*

@Parcelize
@SuppressLint("ParcelCreator")
data class Order(var name: String = "",
            var address: String = "",
            var number: String = "",
            var timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date()),
            var items: String = "",
                 var orderID: String = "",
            var amount: String = "",
            var paymentMethod: String = "") : Parcelable {

    @get:Exclude
    var id = ""

    companion object {
        fun fromSnapshot(snapshot: DocumentSnapshot) : Order? {
            val o = snapshot.toObject(Order::class.java)
            o?.id = snapshot.id
            return o
        }
    }
}