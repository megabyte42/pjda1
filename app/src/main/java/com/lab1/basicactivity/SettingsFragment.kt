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
            view!!.fragment_settings_switch_color_blind_mode.toggle()
        }


        if (colorBlindMode) {
            view = inflater.inflate(R.layout.fragment_settings_0000, container, false)
        }

        view!!.fragment_settings_imageButton_profile.setOnClickListener {
            listener?.onProfileButtonPressed()
        }
        view!!.fragment_settings_switch_color_blind_mode.setOnCheckedChangeListener {buttonView, isChecked ->
            Log.d(Constants.TAG, "color bind check trying to check as: $isChecked")
            listener?.toggleColorBlindMode(isChecked)
        }
        return view
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