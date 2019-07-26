package com.lab1.basicactivity

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.dialog_login.view.*
import kotlinx.android.synthetic.main.fragment_login.view.*

class SplashFragment() : Fragment() {


    var listener: ButtonListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(Constants.TAG, "create splash fragment view")
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        view.fragment_login_button_login.setOnClickListener {
            listener?.onLoginButtonPressed()
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ButtonListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement ButtonListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


}