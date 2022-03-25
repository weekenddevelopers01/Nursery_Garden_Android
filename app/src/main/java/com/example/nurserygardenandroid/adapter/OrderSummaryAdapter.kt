package com.example.nurserygardenandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nurserygardenandroid.R
import com.example.nurserygardenandroid.model.order.OrderItem
import com.example.nurserygardenandroid.utils.Constants
import com.example.nurserygardenandroid.utils.ProdConstants
import kotlinx.android.synthetic.main.order_summary_item.view.*

class OrderSummaryAdapter(val context: Context, val list:List<OrderItem>):RecyclerView.Adapter<OrderSummaryAdapter.ViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.order_summary_item, parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(context)
            .load(ProdConstants.BASE_URL+"image/"+list.get(position).productImage)
            .into(holder.image)

        holder.name.text = list.get(position).productName
        holder.qty.text = list.get(position).qty.toString()+" X "+Constants.RUPEE+list.get(position).price
        holder.total.text = list.get(position).subTotal.toString()

    }

    override fun getItemCount(): Int = list.size


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val name = itemView.orderSummaryName
        val image = itemView.orderSummaryImage
        val qty = itemView.orderSummaryQty
        val total = itemView.orderSummaryTotal
    }
}