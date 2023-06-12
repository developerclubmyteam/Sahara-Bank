package com.rajnish.myapplication.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rajnish.myapplication.db.User
import com.rajnish.myapplication.repository.UserRepository

class MainViewModel :ViewModel(){

    private var repository :UserRepository = UserRepository().getInstance()
    private val _allUsers = MutableLiveData<List<User>>()
    val alluser :LiveData<List<User>> = _allUsers





    init {

        repository.addUser()

        repository.loadUsers(_allUsers)

    }




}