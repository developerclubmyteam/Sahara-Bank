package com.rajnish.myapplication.fragment

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.rajnish.myapplication.R
import com.rajnish.myapplication.databinding.FragmentHomeBinding


class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    lateinit var mobNo : String
    private lateinit var db :DatabaseReference
    private lateinit var mppin:String
    private lateinit var balance :String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)



        val sharedPreferences = context?.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val mobileNumber = sharedPreferences?.getString("mobileNumber", "")
        mobNo = mobileNumber.toString()

        db = FirebaseDatabase.getInstance().reference.child("Account Details")

        balanceRetrive()

        binding.btnBalanceEnquiry.setOnClickListener{

            customDialogForMpin()

        }


    }


    private fun customDialogForMpin(){

        val dialogView = layoutInflater.inflate(R.layout.dialog_check_balance, null)
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setView(dialogView)

        val etMpin = dialogView.findViewById<EditText>(R.id.etMpin)
        val btnCheckBalance = dialogView.findViewById<Button>(R.id.btnCheckBalance)

        val dialog = dialogBuilder.create()
        dialog.show()

        btnCheckBalance.setOnClickListener {
            val mpin = etMpin.text.toString()

            if (mppin == mpin){

                // Inside HomeFragment
                val navController = findNavController()
                val bundle = Bundle()
                bundle.putString("balanceAmount", balance) // Replace "100" with your actual balance value
                navController.navigate(R.id.action_homeFragment_to_checkBalanceFragment, bundle)

                dialog.dismiss()


            }else{


                Toast.makeText(context, "invalid m-pin", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }


        }


    }

    private fun balanceRetrive() {

        if (mobNo.isNotEmpty()){

            db.child(mobNo).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val mpin = snapshot.child("mpin").value as String?
                        val balances = snapshot.child("balance").value as String?
                        mppin = mpin.toString()
                        balance = balances.toString()

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle onCancelled event
                }
            })
        }


    }

}
