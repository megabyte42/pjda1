package com.lab1.basicactivity

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
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
                 var amount: Number = 0,
                 var tip: Number = 0,
                 var paymentMethod: String = "",
                 var time: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())) : Parcelable {

    @get:Exclude
    var id = ""

    companion object {
        fun fromSnapshot(snapshot: DocumentSnapshot) : Route? {
            val r = snapshot.toObject(Route::class.java)
            r?.id = snapshot.id
            return r
        }
    }
}