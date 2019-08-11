package com.lab1.basicactivity

import com.google.firebase.Timestamp
import java.io.Serializable

class TimeStampObject(time: String) : Serializable {
    var timestamp: String = time
}