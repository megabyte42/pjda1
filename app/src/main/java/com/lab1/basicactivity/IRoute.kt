package com.lab1.basicactivity

interface IRoute {
    fun onSendRoutingAssignment(order: Order)
    fun onRouteAccepted()
    fun onOrderConfirmed()
    fun sendLoginInfoBack(status: Boolean)
}