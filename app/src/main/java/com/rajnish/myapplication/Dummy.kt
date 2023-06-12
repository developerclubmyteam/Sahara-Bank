package com.rajnish.myapplication


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.rajnish.myapplication.databinding.ActivityDummyBinding
import com.rajnish.myapplication.db.Notes


class Dummy : AppCompatActivity() {
    private lateinit var binding: ActivityDummyBinding
    private lateinit var notesArrayList: ArrayList<Notes>
    private lateinit var noteArraAdapter: ArrayAdapter<Notes>
    private lateinit var db :DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDummyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        notesArrayList = arrayListOf()
        noteArraAdapter = CustomAdapter(this, notesArrayList)

        db = FirebaseDatabase.getInstance().reference.child("Note List")
        retriveNotes()

        binding.listview.adapter = noteArraAdapter




        binding.flatActionButton.setOnClickListener {
            alertDialog()

        }

     binding.listview.setOnItemClickListener { parent, view, position, id ->

         if(notesArrayList.size > 0){

             db.child(notesArrayList[position].id.toString()).setValue(null)
             noteArraAdapter.notifyDataSetChanged()
         }else{
             Toast.makeText(this, "Bhag bhutni ke", Toast.LENGTH_SHORT).show()
         }

     }

    }


    fun alertDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.aletbox, null)
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setView(dialogView)
        alertDialog.setPositiveButton("Ok") { dialog, which ->

            val title = dialogView.findViewById<EditText>(R.id.title)
            val desc = dialogView.findViewById<EditText>(R.id.desc)

            notesArrayList.add(Notes(title.text.toString(), desc.text.toString()))
            noteArraAdapter.notifyDataSetChanged()
            val id = db.push().key
            db.child(id!!).setValue(Notes(title.text.toString(),desc.text.toString(),id))




        }

        alertDialog.setNegativeButton("Cancel") { dialog, which ->
            dialog.dismiss()

        }

        alertDialog.show()


    }


    fun retriveNotes(){
        db.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                notesArrayList.clear()
               if (snapshot.exists()){
                   for (data in snapshot.children){
                       val title = data.child("title").value as String
                       val des = data.child("discription").value as String
                       val id = data.child("id").value as String

                       notesArrayList.add(Notes(title,des,id))
                       noteArraAdapter.notifyDataSetChanged()
                   }
               }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}



