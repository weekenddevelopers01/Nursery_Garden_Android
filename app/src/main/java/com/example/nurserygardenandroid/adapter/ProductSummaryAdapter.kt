package com.example.nurserygardenandroid.adapter

import android.app.Activity
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nurserygardenandroid.R
import com.example.nurserygardenandroid.model.products.Products
import com.example.nurserygardenandroid.model.products.image
import com.example.nurserygardenandroid.ui.activity.CartActivity
import com.example.nurserygardenandroid.ui.activity.OrderSummaryActivity
import com.example.nurserygardenandroid.utils.ProdConstants
import kotlinx.android.synthetic.main.summarylist_item.view.*
import java.util.zip.Inflater

class ProductSummaryAdapter(val context:Activity, val list: List<Products>):
    RecyclerView.Adapter<ProductSummaryAdapter.ViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v = LayoutInflater.from(context).inflate(R.layout.summarylist_item,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

//        val imageBytes = Base64.decode(list.get(position).image, Base64.DEFAULT)
        Glide.with(context).load(ProdConstants.Companion.BASE_URL+"image/"+list.get(position).productImage).into(holder.productImage)
        holder.productName.setText(list.get(position).productName)
        val denomination = ""+list.get(position).productPrice+" X "+list.get(position).qty+ " = "+String.format("%.2f",list.get(position).productPrice* list.get(position).qty)
        holder.productDenomination.setText(denomination)

        calculate()
    }

    override fun getItemCount(): Int = list.size


    private fun calculate(){
        var total = 0.0
        var flag = true
        for(p in list){
            var subTotal = p.qty * p.productPrice
            total += subTotal
            if(!p.status){
                flag=false
            }
        }

        var activity = context as OrderSummaryActivity
        activity.totalPriceCallBack(total, flag)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage = itemView.productImage
        val productName = itemView.productName
        val productDenomination = itemView.subDenomination

    }
}