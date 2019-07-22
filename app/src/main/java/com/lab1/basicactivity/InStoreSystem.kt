package com.lab1.basicactivity

import android.util.Log

class InStoreSystem {

    var employees = mutableMapOf("12345" to "password")
    var clockStatus = mutableMapOf("12345" to 0) //0 clocked out ; 1 clocked in
    var loginStatus = mutableMapOf("12345" to true, "11111" to false)


    fun loginEmployee(employeeNumber: String, employeePassword: String): Boolean {
        if (employees.containsKey(employeeNumber)) {
            if (employees[employeeNumber] == (employeePassword)) {
                loginStatus[employeeNumber] = true
                Log.d(Constants.TAG, "employee logged in")
                return true
            }
        }
        return false
    }

}