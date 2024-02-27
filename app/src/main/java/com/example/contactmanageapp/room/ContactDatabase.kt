package com.example.contactmanageapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Contact::class], version = 1)
abstract class ContactDatabase : RoomDatabase(){

    abstract val contactDAO: ContactDAO

    //Singleton Design Pattern
    //only one single database instance of this class will be available in the app
    companion object{//define static members of a class
        @Volatile
        private var INSTANCE: ContactDatabase?= null//nullable

        fun getInstance(context: Context): ContactDatabase {
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    //Creating the database object
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ContactDatabase::class.java,
                        "contacts_db"
                    ).build()
                }
                INSTANCE = instance
                return instance
            }
        }
    }

}