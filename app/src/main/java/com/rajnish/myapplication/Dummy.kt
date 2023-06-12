package com.rajnish.myapplication


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.rajnish.myapplication.databinding.ActivityDummyBinding
import com.rajnish.myapplication.db.MyDetails



class Dummy : AppCompatActivity()  {
    private lateinit var binding: ActivityDummyBinding
    private lateinit var db :DatabaseReference
    private lateinit var arrylist:ArrayList<MyDetails>
    private lateinit var arrayadapter:ArrayAdapter<MyDetails>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDummyBinding.inflate(layoutInflater)
        setContentView(binding.root)

      arrylist = arrayListOf()
        arrayadapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,arrylist)

        binding.listView.adapter=arrayadapter


        db = FirebaseDatabase.getInstance().reference.child("My Details")

        binding.floatactionbtn.setOnClickListener{

           showCustomAlertDialog()
            arrayadapter.notifyDataSetChanged()
        }

        retriveData()


    }

    fun retriveData(){

        db.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                arrylist.clear()

                for (data in snapshot.children){
                    val name = data.child("name").value as String
                    val fname = data.child("fname").value as String
                    val mob_no = data.child("mob_no").value as String
                    val email_Id = data.child("email_Id").value as String
                    val password = data.child("password").value as String


                  arrylist.add( MyDetails(name,fname,mob_no,email_Id,password))

                    arrayadapter.notifyDataSetChanged()
                }



            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

    }

    fun showCustomAlertDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.aletbox, null)
        val name = dialogView.findViewById<EditText>(R.id.name)
        val fname = dialogView.findViewById<EditText>(R.id.fname)
        val mob = dialogView.findViewById<EditText>(R.id.mob)
        val eid = dialogView.findViewById<EditText>(R.id.eid)
        val pass = dialogView.findViewById<EditText>(R.id.pass)

        val alertDialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Enter Details")
            .setPositiveButton("OK") { dialog, which ->
                val name = name.text.toString()
                val fname = fname.text.toString()
                val mob = mob.text.toString()
                val id = eid.text.toString()
                val pass = pass.text.toString()
                db.push().setValue(MyDetails(name,fname,mob,id,pass))
            }
            .setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

}
