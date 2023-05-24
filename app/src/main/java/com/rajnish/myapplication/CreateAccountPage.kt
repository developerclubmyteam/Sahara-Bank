package com.rajnish.myapplication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.rajnish.myapplication.databinding.ActivityCreateAccountPageBinding
import com.rajnish.myapplication.db.UserDetails

class CreateAccountPage : AppCompatActivity() {

    private lateinit var binding: ActivityCreateAccountPageBinding
    private lateinit var db:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAccountPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseDatabase.getInstance().reference.child("Account Details")


        binding.btnCreateAccount.setOnClickListener{
            val edMpin = binding.etMpin.text.toString()
            val edName = binding.etFullName.text.toString()
            val edMob = binding.etEmail.text.toString()
            val edAmount = binding.etAmount.text.toString()

                val userDetails = UserDetails(edName,edMob,edMpin,edAmount)


            db.child(edMob).setValue(userDetails)


            // Store mobile number in SharedPreferences
            val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("mobileNumber", edMob)
            editor.apply()


            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)




        }



    }
}