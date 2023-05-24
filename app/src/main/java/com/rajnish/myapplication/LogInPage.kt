package com.rajnish.myapplication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.rajnish.myapplication.databinding.ActivityLogInPageBinding

class LogInPage : AppCompatActivity() {

    private lateinit var binding: ActivityLogInPageBinding
    private lateinit var db :DatabaseReference
     lateinit var mobNo : String
    lateinit var mppin : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseDatabase.getInstance().reference.child("Account Details")


        if (!isLogin()) {
            val intent = Intent(this, CreateAccountPage::class.java)
            startActivity(intent)

        }

        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val mobileNumber = sharedPreferences.getString("mobileNumber", "")
        mobNo = mobileNumber.toString()

        mpinRetrive()


        binding.btnLogin.setOnClickListener{

            val  edMpin = binding.edMpin.text.toString()


          if (edMpin == mppin){

              val  intent = Intent(this,MainActivity::class.java)
              startActivity(intent)

          }
            else{
                binding.edMpin.error = "Invalid Mpin"
          }

        }

        binding.tvCreateAccount.setOnClickListener {
            val intent = Intent(this, CreateAccountPage::class.java)
            startActivity(intent)

        }


    }


    private fun mpinRetrive() {

        if (mobNo.isNotEmpty()){
            binding.tvCreateAccount.visibility = View.GONE


            db.child(mobNo).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val mpin = snapshot.child("mpin").value as String?

                    mppin = mpin.toString()

                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled event
            }
        })
        }
        else {
            binding.tvCreateAccount.visibility = View.VISIBLE
        }

    }

    private fun isLogin(): Boolean {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val mobileNumber = sharedPreferences.getString("mobileNumber", "")
         mobNo = mobileNumber.toString()
        return !mobileNumber.isNullOrEmpty()
    }
}