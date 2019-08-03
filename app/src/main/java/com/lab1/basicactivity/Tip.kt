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
data class Tip(var amount: String = "", var employeeNum: String = ""): Parcelable {

    @get:Exclude
    var id = ""
    @ServerTimestamp
    var time: Timestamp? = null

    companion object {
        const val TIP_KEY = "time"
        fun fromSnapshot(snapshot: DocumentSnapshot) : Tip? {
            val t = snapshot.toObject(Tip::class.java)
            t?.id = snapshot.id
            return t
        }
    }
}