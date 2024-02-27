package com.example.contactmanageapp.viewmodel


import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactmanageapp.repository.ContactRepository
import com.example.contactmanageapp.room.Contact
import kotlinx.coroutines.launch

//viewmodel: store and manage UI-related Data
//separating the UI-related logic from UI Controller(Activity/Frag.)
class ContactViewModel(private val repository: ContactRepository)
    :ViewModel(), Observable {

    val contacts = repository.contacts
    private var isUpdateorDelete = false
    private lateinit var contactToUpdateorDelete: Contact

    //Data Binding with Live Data
    @Bindable
    val inputName = MutableLiveData<String?>()

    @Bindable
    val inputEmail = MutableLiveData<String?>()

    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()

    @Bindable
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }
    fun insert(contact: Contact) = viewModelScope.launch {
        repository.insert(contact)
    }
    fun saveOrUpdate(){
        if(isUpdateorDelete){
            //make an update
            contactToUpdateorDelete.contact_name = inputName.value!!
            contactToUpdateorDelete.contact_email = inputEmail.value!!
            update(contactToUpdateorDelete)
        }else{
            //inserting a new contact
            val name = inputName.value!!
            val email = inputEmail.value!!

            insert(Contact(0, name, email))

            //Reset the name and email
            inputEmail.value = null
            inputName.value = null
        }
    }
    fun delete(contact: Contact) = viewModelScope.launch {
        repository.delete(contact)

        //Reseting the buttons and fields
        inputName.value = null
        inputEmail.value = null
        isUpdateorDelete = false
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }
    fun update(contact: Contact)= viewModelScope.launch{
        repository.update(contact)

        //Reseting the buttons and fields
        inputName.value = null
        inputEmail.value = null
        isUpdateorDelete = false
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }
    fun clearAll() = viewModelScope.launch {
        repository.deleteALl()
    }
    fun clearAllorDelete(){
        if(isUpdateorDelete){
            delete(contactToUpdateorDelete)
        }
        else{
            clearAll()
        }
    }
    fun initUpdateAndDelete(contact: Contact){
        inputEmail.value = contact.contact_email
        inputName.value = contact.contact_name
        isUpdateorDelete = true
        contactToUpdateorDelete = contact
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value= "Delete"
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}