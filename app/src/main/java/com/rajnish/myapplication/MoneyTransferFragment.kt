package com.rajnish.myapplication

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.rajnish.myapplication.databinding.FragmentMoneyTransferBinding


class MoneyTransferFragment : Fragment(R.layout.fragment_money_transfer) {

    private lateinit var binding:FragmentMoneyTransferBinding
    private lateinit var db:DatabaseReference
    private var balance: String? = null
    private var MyBalance: String? = null
    private var mobNO: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMoneyTransferBinding.bind(view)

        db = FirebaseDatabase.getInstance().reference.child("Account Details")


        val sharedPreferences = context?.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val mobileNumber = sharedPreferences?.getString("mobileNumber", "")
        mobNO = mobileNumber.toString()
        balanceRetrive(mobNO!!)


        binding.btnSend.setOnClickListener {
            val edMob = binding.edMob.text.toString()
            balanceRetrive(edMob)

            val edAmount = binding.edAmount.text.toString()

            val previousBalance = balance?.toIntOrNull()
            val sendBalance = edAmount.toIntOrNull()

            val MypreviousBalance = MyBalance?.toIntOrNull()
            val MysendBalance = edAmount.toIntOrNull()

            val result = previousBalance?.let { previous ->
                sendBalance?.let { send ->
                    previous + send
                }
            }

            val Myresult = MypreviousBalance?.let { previous ->
                MysendBalance?.let { send ->
                    previous - send
                }
            }

            result?.let { balanceResult ->
                db.child(edMob).child("balance").setValue(balanceResult.toString())
                balanceRetrive(edMob)

            }
            Myresult?.let { balanceResult ->

                db.child(mobNO!!).child("balance").setValue(balanceResult.toString())
                balanceRetrive(mobNO!!)
            }



        }


    }


     fun balanceRetrive(mob:String)  {

            db.child(mob).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val balances = snapshot.child("balance").value as String?
                        if (mob==mobNO){
                            MyBalance = balances
                        }
                        else{
                            balance = balances.toString()
                        }

                        Toast.makeText(context, ""+balances, Toast.LENGTH_SHORT).show()



                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle onCancelled event
                }
            })
        }

    }


