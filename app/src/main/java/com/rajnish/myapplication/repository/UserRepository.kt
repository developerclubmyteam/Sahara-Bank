package com.rajnish.myapplication.repository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.rajnish.myapplication.db.User
import java.lang.Exception

class UserRepository {
    private val db: DatabaseReference = FirebaseDatabase.getInstance().reference.child("User")

    @Volatile private var INSTANCE: UserRepository? = null

    fun getInstance(): UserRepository {

        return INSTANCE ?: synchronized(this) {
            var instance = UserRepository()
            INSTANCE = instance
            instance
        }
    }

    fun loadUsers(userList: MutableLiveData<List<User>>) {

        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val _userList: List<User> = snapshot.children.map { dataSnapshot ->
                        dataSnapshot.getValue(User::class.java)!!
                    }
                    userList.postValue(_userList)
                } catch (e: Exception) {

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun addUser() {
        db.push().setValue(User("Rajnish", "898989898", "90"))
    }
}