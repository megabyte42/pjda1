package com.lab1.basicactivity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_recent_deliveries.view.*

class RecentDeliveriesFragment: Fragment() {

    var listener: ButtonListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(Constants.TAG, "create profile fragment view")
        val view = inflater.inflate(R.layout.fragment_recent_deliveries, container, false)
        view.fragment_recent_deliveries_imageButton_profile.setOnClickListener {
            listener?.onProfileButtonPressed()
        }
        view.fragment_recent_deliveries_imageButton_settings.setOnClickListener {
            listener?.onSettingsButtonPressed()
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
        //listener = null
    }
}