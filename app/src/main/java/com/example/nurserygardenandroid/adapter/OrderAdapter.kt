package com.example.nurserygardenandroid.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nurserygardenandroid.R
import com.example.nurserygardenandroid.model.order.Order
import com.example.nurserygardenandroid.model.products.Products
import com.example.nurserygardenandroid.ui.activity.OrderActivity
import com.example.nurserygardenandroid.ui.activity.OrderDetailActivity
import com.example.nurserygardenandroid.utils.Constants
import com.example.nurserygardenandroid.utils.ProdConstants
import kotlinx.android.synthetic.main.order_item.view.*

class OrderAdapter(val context: Context, val list:List<Order>):
    RecyclerView.Adapter<OrderAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.order_item, parent, false));

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = list.get(position)
        Glide.with(context)
            .load(ProdConstants.BASE_URL+"image/"+order.orderItems.get(0).productImage)
            .into(holder.orderImage)
        var fI = order!!.date!!.indexOf("T");
        holder.orderId.text = "ID-"+order.invoiceNo
        holder.orderDate.text = order.date?.substring(0, fI);
        holder.orderPrice.text = Constants.RUPEE+order.grandTotal.toString()
        holder.orderStatus.text = order.status

        holder.holder.setOnClickListener({
            val _id = list.get(position)._id
            val activity = context as OrderActivity
            activity.intent = Intent(activity, OrderDetailActivity::class.java)
            activity.intent.putExtra(Constants.EXTRA_ORDER_ID, _id)
            activity.startActivity(activity.intent)
        })
    }

    override fun getItemCount() = list.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val orderImage = itemView.orderImage
        val orderId = itemView.orderId
        val orderDate = itemView.orderDate
        val orderPrice = itemView.orderPrice
        val orderStatus = itemView.orderStatus
        val holder = itemView.holder
    }

}