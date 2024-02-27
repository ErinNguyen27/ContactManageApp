package com.example.contactmanageapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactmanageapp.databinding.ActivityMainBinding
import com.example.contactmanageapp.repository.ContactRepository
import com.example.contactmanageapp.room.Contact
import com.example.contactmanageapp.room.ContactDatabase
import com.example.contactmanageapp.view.MyRecyclerViewAdaptr
import com.example.contactmanageapp.viewmodel.ContactViewModel
import com.example.contactmanageapp.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var contactViewModel : ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //ROOM database
        val dao = ContactDatabase.getInstance(applicationContext).contactDAO
        val repository = ContactRepository(dao)
        val factory = ViewModelFactory(repository)

        //ViewModel
        contactViewModel = ViewModelProvider(this, factory)
            .get(ContactViewModel::class.java)
        binding.contactViewModel = contactViewModel

        binding.lifecycleOwner = this

        initRecyclerView()



    }
    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        DisplayUsersList()
    }

    private fun DisplayUsersList() {
        contactViewModel.contacts.observe(this, Observer {
            binding.recyclerView.adapter = MyRecyclerViewAdaptr(
                it, {selectedItem: Contact -> listItemClicked(selectedItem)}
            )
        })

    }
    private fun listItemClicked(selectedItem: Contact){
        Toast.makeText(this,
            "Selected name is ${selectedItem.contact_name}",
            Toast.LENGTH_LONG).show()

        contactViewModel.initUpdateAndDelete(selectedItem)
    }
}