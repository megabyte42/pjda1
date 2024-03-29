package com.lab1.basicactivity

interface ButtonListener {
    fun onLoginButtonPressed()
    fun onProfileButtonPressed()
    fun onSettingsButtonPressed()
    fun onRecentDeliveriesButtonPressed()
    fun onTriggerSoftware()
    fun onPhonePresesd(phone: String)
    fun toggleColorBlindMode(checked: Boolean)
    fun onHomeButtonPressed()
}