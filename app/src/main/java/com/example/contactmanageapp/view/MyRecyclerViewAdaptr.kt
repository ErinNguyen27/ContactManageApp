package com.example.contactmanageapp.view

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.contactmanageapp.R
import com.example.contactmanageapp.databinding.CardItemBinding
import com.example.contactmanageapp.room.Contact

class MyRecyclerViewAdaptr(private val contactsList:List<Contact>,
    private val clickListener: (Contact)-> Unit): RecyclerView.Adapter<MyRecyclerViewAdaptr.MyViewHolder>(){

    class MyViewHolder(val binding: CardItemBinding)
    : RecyclerView.ViewHolder(binding.root){
        fun bind(contact: Contact, clickListener: (Contact)-> Unit){

            binding.tvName.text = contact.contact_name
            binding.tvEmail.text = contact.contact_email

            binding.listItemLayout.setOnClickListener{
                clickListener(contact)
            }
        }

}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: CardItemBinding = DataBindingUtil.
                inflate(layoutInflater,
                    R.layout.card_item,
                    parent, false)
        return MyViewHolder(binding)
        Log.d("Holder","one created")
    }

    override fun getItemCount(): Int {
        return contactsList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(contactsList[position], clickListener)
    }
}