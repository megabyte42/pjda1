package com.lab1.basicactivity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_settings.view.*

class SettingsFragment: Fragment() {

    var listener: ButtonListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(Constants.TAG, "create profile fragment view")

        var view = inflater.inflate(R.layout.fragment_settings, container, false)
        if (colorBlindMode) {
            Log.d(Constants.TAG, "toggle should be to right")
            view = inflater.inflate(R.layout.fragment_settings_0000, container, false)

        }

        updateView(view)
//        if (colorBlindMode) {
//            view = inflater.inflate(R.layout.fragment_settings_0000, container, false)
//        }

        view!!.fragment_settings_imageButton_profile.setOnClickListener {
            listener?.onProfileButtonPressed()
        }
        view!!.fragment_settings_switch_color_blind_mode.setOnCheckedChangeListener {buttonView, isChecked ->
            Log.d(Constants.TAG, "color bind check trying to check as: $isChecked")
            listener?.toggleColorBlindMode(isChecked)
        }
        view!!.fragment_settings_switch_navigation.setOnCheckedChangeListener {buttonView, isChecked ->
            navigationBoth = isChecked
            if (isChecked) {
                navigationFromStore = isChecked
                navigationToStore = isChecked
                view!!.fragment_settings_switch_navigation_delivery.isChecked = navigationFromStore
                view!!.fragment_settings_switch_navigation_to_store.isChecked = navigationToStore
            } else {
                navigationFromStore = isChecked
                navigationToStore = isChecked
                view!!.fragment_settings_switch_navigation_delivery.isChecked = navigationFromStore
                view!!.fragment_settings_switch_navigation_to_store.isChecked = navigationToStore
            }
        }
        view!!.fragment_settings_switch_navigation_delivery.setOnCheckedChangeListener {buttonView, isChecked ->
            navigationFromStore = isChecked
            if (navigationToStore && navigationFromStore) {
                navigationBoth = true
                view!!.fragment_settings_switch_navigation.isChecked = navigationBoth
            } else  {
                navigationBoth = false
                view!!.fragment_settings_switch_navigation.isChecked = navigationBoth
            }
        }
        view!!.fragment_settings_switch_navigation_to_store.setOnCheckedChangeListener {buttonView, isChecked ->
            navigationToStore = isChecked
            if (navigationFromStore && navigationToStore) {
                navigationBoth = true
                view!!.fragment_settings_switch_navigation.isChecked = navigationBoth
            } else  {
                navigationBoth = false
                view!!.fragment_settings_switch_navigation.isChecked = navigationBoth
            }
        }
        view!!.fragment_settings_imageButton_logo.setOnClickListener {
            listener?.onHomeButtonPressed()
        }
        return view
    }

    private fun updateView(view: View) {
        view!!.fragment_settings_switch_navigation.isChecked = navigationBoth
        view!!.fragment_settings_switch_navigation_delivery.isChecked = navigationFromStore
        view!!.fragment_settings_switch_navigation_to_store.isChecked = navigationToStore
        view!!.fragment_settings_switch_color_blind_mode.isChecked = colorBlindMode
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ButtonListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement ButtonListener") as Throwable
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

}