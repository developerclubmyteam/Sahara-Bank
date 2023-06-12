package com.rajnish.myapplication.classp


import android.content.Context
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.rajnish.myapplication.R


class Dialog {

    interface DialogCallback {
        fun onDialogPositiveClick(title: String, description: String)
    }


}

