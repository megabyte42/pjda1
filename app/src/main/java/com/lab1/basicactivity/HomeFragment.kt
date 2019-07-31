package com.lab1.basicactivity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_login.view.*

class HomeFragment: Fragment() {

    var listener: ButtonListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(Constants.TAG, "create home fragment view")
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        view.fragment_home_imageButton_profile.setOnClickListener {
            listener?.onProfileButtonPressed()
        }
        view.fragment_home_imageButton_settings.setOnClickListener {
            listener?.onSettingsButtonPressed()
        }
        view.fragment_home_button_recent_deliveries.setOnClickListener {
            listener?.onRecentDeliveriesButtonPressed()
        }
        view.fragment_home_imageView_pizza_background.setOnClickListener {
            listener?.onTriggerSoftware()
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