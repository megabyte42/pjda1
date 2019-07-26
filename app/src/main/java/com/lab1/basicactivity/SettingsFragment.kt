package com.lab1.basicactivity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_settings.view.*

class SettingsFragment: Fragment() {

    var listener: ButtonListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(Constants.TAG, "create profile fragment view")
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        view.fragment_settings_imageButton_profile.setOnClickListener {
            listener?.onProfileButtonPressed()
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