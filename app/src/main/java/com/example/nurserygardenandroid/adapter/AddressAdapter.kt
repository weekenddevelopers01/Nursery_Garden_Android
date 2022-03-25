package com.example.nurserygardenandroid.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nurserygardenandroid.R
import com.example.nurserygardenandroid.model.user.Address
import com.example.nurserygardenandroid.ui.activity.AddressActivity
import com.example.nurserygardenandroid.ui.fragment.home.CustomAdapter
import kotlinx.android.synthetic.main.address_list_item.view.*

class AddressAdapter(val activity: Context, val list: ArrayList<Address>, val isAddressSelection:Boolean):
    RecyclerView.Adapter<AddressAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(activity).inflate(R.layout.address_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list.get(position)

        holder.name.setText(item.name)
        holder.address.setText(item.address)
        holder.city.setText(item.city)
        holder.stateAndZipCode.setText(item.state+" - "+item.zipcode)
        holder.phone.setText(item.phone)
        holder.delete.setOnClickListener {
            var addressActivity: AddressActivity = activity as AddressActivity
            addressActivity.deleteAddress(item._id!!)
        }
        holder.container.setOnClickListener{
            if(isAddressSelection){
                val addressActivity = activity as AddressActivity
                addressActivity.toOrderSummary(item)
            }
        }



    }

    override fun getItemCount()= list.size


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val name = itemView.address_name
        val address = itemView.address_address
        val city = itemView.address_city
        val stateAndZipCode = itemView.address_state_zipcode
        val phone = itemView.address_phone
        val delete = itemView.delete
        val container = itemView.container

    }
}