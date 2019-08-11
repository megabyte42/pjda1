package com.lab1.basicactivity

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.Parcelize
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
@SuppressLint("ParcelCreator")
data class Route(var address: String = "",
                 var name: String = "",
                 var phone: String = "",
                 var orderNum: String = "",
                 var desc: String = "",
                 var instructions: String = "",
                 var amount: String = "",
                 var tip: String = "",
                 var paymentMethod: String = "") : Parcelable {

    @get:Exclude
    var id = ""
    @ServerTimestamp
    var time: com.google.firebase.Timestamp? = null

    companion object {
        const val ROUTE_KEY = "time"
        fun fromSnapshot(snapshot: DocumentSnapshot) : Route? {
            val r = snapshot.toObject(Route::class.java)
            r?.id = snapshot.id
            return r
        }
    }
}