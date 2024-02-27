package com.example.contactmanageapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.contactmanageapp.repository.ContactRepository
import java.lang.IllegalArgumentException

class ViewModelFactory(private val repository: ContactRepository)
    : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T{
            if (modelClass.isAssignableFrom(ContactViewModel::class.java)){
                return ContactViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown View Model Class")
        }

}