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
        Log.d(Constants.TAG, "create recent deliveries fragment view")
        val adapter = RecentDeliveriesAdapter(context!!, this)
        val view = inflater.inflate(R.layout.fragment_recent_deliveries, container, false)
        view.fragment_recent_deliveries_imageButton_profile.setOnClickListener {
            listener?.onProfileButtonPressed()
        }
        view.fragment_recent_deliveries_imageButton_settings.setOnClickListener {
            listener?.onSettingsButtonPressed()
        }
        view.fragment_recent_deliveries_order_phone1.setOnClickListener {
            listener?.onPhonePresesd(view.fragment_recent_deliveries_order_phone1.text.toString())
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

    fun updateRow(count: Int, orderNum: String, name: String, phone: String) {
        when (count) {
            1 -> {view!!.fragment_recent_deliveries_order_number1.text = orderNum
                  view!!.fragment_recent_deliveries_order_name1.text = name
                  view!!.fragment_recent_deliveries_order_phone1.text = phone}
            2 -> {view!!.fragment_recent_deliveries_order_number2.text = orderNum
                view!!.fragment_recent_deliveries_order_name2.text = name
                view!!.fragment_recent_deliveries_order_phone2.text = phone}
            3 -> {view!!.fragment_recent_deliveries_order_number3.text = orderNum
                view!!.fragment_recent_deliveries_order_name3.text = name
                view!!.fragment_recent_deliveries_order_phone3.text = phone}
            4 -> {view!!.fragment_recent_deliveries_order_number4.text = orderNum
                view!!.fragment_recent_deliveries_order_name4.text = name
                view!!.fragment_recent_deliveries_order_phone4.text = phone}
            5 -> {view!!.fragment_recent_deliveries_order_number5.text = orderNum
                view!!.fragment_recent_deliveries_order_name5.text = name
                view!!.fragment_recent_deliveries_order_phone5.text = phone}
            6 -> {view!!.fragment_recent_deliveries_order_number6.text = orderNum
                view!!.fragment_recent_deliveries_order_name6.text = name
                view!!.fragment_recent_deliveries_order_phone6.text = phone}
            7 -> {view!!.fragment_recent_deliveries_order_number7.text = orderNum
                view!!.fragment_recent_deliveries_order_name7.text = name
                view!!.fragment_recent_deliveries_order_phone7.text = phone}
            8 -> {view!!.fragment_recent_deliveries_order_number8.text = orderNum
                view!!.fragment_recent_deliveries_order_name8.text = name
                view!!.fragment_recent_deliveries_order_phone8.text = phone}
            9 -> {view!!.fragment_recent_deliveries_order_number9.text = orderNum
                view!!.fragment_recent_deliveries_order_name9.text = name
                view!!.fragment_recent_deliveries_order_phone9.text = phone}
            10 -> {view!!.fragment_recent_deliveries_order_number10.text = orderNum
                view!!.fragment_recent_deliveries_order_name10.text = name
                view!!.fragment_recent_deliveries_order_phone10.text = phone}
        }
    }
}