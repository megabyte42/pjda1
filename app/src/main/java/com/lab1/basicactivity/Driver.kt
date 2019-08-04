package com.lab1.basicactivity

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
@SuppressLint("ParcelCreator")
class Driver(var clockStatus: Boolean = false, var employeeNum: String = "", var password: String = ""): Parcelable {


    @get:Exclude
    var id = ""

    companion object {
        fun fromSnapshot(snapshot: DocumentSnapshot) : Driver? {
            val d = snapshot.toObject(Driver::class.java)
            d?.id = snapshot.id
            return d
        }
    }
}