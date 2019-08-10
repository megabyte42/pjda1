package com.lab1.basicactivity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment(employeeNum: String): Fragment() {

    var listener: ButtonListener? = null


    private var employeeNum = employeeNum

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(Constants.TAG, "create profile fragment view")
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val adapter = ProfileAdapter(context!!, employeeNum)
        view.fragment_profile_imageButton_settings.setOnClickListener {
            listener?.onSettingsButtonPressed()
        }
        view.mstb_multi_id.setOnValueChangedListener { position ->
            adapter.updateTable(position, this)
        }
        view.fragment_profile_toggleButton_clock.setOnClickListener {
            adapter.updateProfileInfo(this)
        }

        adapter.updateTable(0, this)
        adapter.updateProfileInfo(this)


        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ButtonListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnProfileButtonPressedListener") as Throwable
        }
    }

    override fun onDetach() {
        super.onDetach()
        //listener = null
    }

    fun updateHoursScheduled(count: String) {
        view!!.fragment_profile_textView_hours_scheduled.text = count
    }

    fun updateTipTotal(total: String) {
        view!!.fragment_profile_textView_tip_total.text = total
    }

    fun updateName(name: String) {
        view!!.fragment_profile_textView_driver_name.text = name
    }

    fun updateNumber(num: String) {
        view!!.fragment_profile_textView_driver_number.text = num
    }

    fun updateClockStatus(clock: Boolean, time: String) {
        if (clock) {
            view!!.fragment_profile_toggleButton_clock.toggle()
            view!!.fragment_profile_textView_clock_time.text = time
            view!!.fragment_profile_textView_clock_status.text = "Clocked In -"
        } else {
            view!!.fragment_profile_toggleButton_clock.toggle()
            view!!.fragment_profile_textView_clock_time.text = time
            view!!.fragment_profile_textView_clock_status.text = "Clocked Out -"
        }
    }

}