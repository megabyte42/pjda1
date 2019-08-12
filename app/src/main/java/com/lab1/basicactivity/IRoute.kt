package com.lab1.basicactivity

interface IRoute {
    fun onSendRoutingAssignment(route: Route)
    fun onRouteAccepted()
    fun onOrderConfirmed()
    fun sendLoginInfoBack(status: Boolean)
    fun onArrived()
    fun setDriver(d: Driver)
}