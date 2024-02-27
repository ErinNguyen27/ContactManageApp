package com.example.contactmanageapp.repository

import com.example.contactmanageapp.room.Contact
import com.example.contactmanageapp.room.ContactDAO

//acts a brige between the ViewModel and Data Source
class ContactRepository(private val contactDAO: ContactDAO) {
    val contacts = contactDAO.getAllContactsInDB()

    suspend fun insert(contact: Contact):Long{
        return contactDAO.insertContact(contact)
    }
    suspend fun delete(contact: Contact){
        return contactDAO.deleteContact(contact)
    }
    suspend fun update(contact: Contact){
        return contactDAO.updateContact(contact)
    }
    suspend fun deleteALl(){
        return contactDAO.deleteAll()
    }

}