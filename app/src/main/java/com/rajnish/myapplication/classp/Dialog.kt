package com.rajnish.myapplication.classp


import android.content.Context
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.rajnish.myapplication.R
import com.rajnish.myapplication.db.MyDetails


class Dialog {

    interface DialogCallback {
        fun onDialogPositiveClick(title: String, description: String)
    }

//    fun showCustomAlertDialog() {
//        val dialogView = LayoutInflater.from(this).inflate(R.layout.aletbox, null)
//        val name = dialogView.findViewById<EditText>(R.id.name)
//        val fname = dialogView.findViewById<EditText>(R.id.fname)
//        val mob = dialogView.findViewById<EditText>(R.id.mob)
//        val eid = dialogView.findViewById<EditText>(R.id.eid)
//        val pass = dialogView.findViewById<EditText>(R.id.pass)
//
//        val alertDialogBuilder = AlertDialog.Builder(this)
//            .setView(dialogView)
//            .setTitle("Enter Details")
//            .setPositiveButton("OK") { dialog, which ->
//                val name = name.text.toString()
//                val fname = fname.text.toString()
//                val mob = mob.text.toString()
//                val id = eid.text.toString()
//                val pass = pass.text.toString()
//                db.push().setValue(MyDetails(name,fname,mob,id,pass))
//            }
//            .setNegativeButton("Cancel") { dialog, which ->
//                dialog.dismiss()
//            }
//
//        val alertDialog = alertDialogBuilder.create()
//        alertDialog.show()
//    }


}

