package com.example.nurserygardenandroid.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nurserygardenandroid.R
import com.example.nurserygardenandroid.model.products.Products
import com.example.nurserygardenandroid.ui.activity.ProductDetailActivity
import com.example.nurserygardenandroid.utils.Constants
import com.example.nurserygardenandroid.utils.ProdConstants
import kotlinx.android.synthetic.main.product_list_item.view.*

class ProductAdapter(val activity: FragmentActivity?, val list:List<Products>): RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(activity).inflate(R.layout.product_list_item, parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list.get(position)

        holder.productName.setText(item.productName)
        holder.productPrice.setText(item.productPrice.toString())
//        val imageBytes = Base64.decode(item.image, Base64.DEFAULT)
//        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

        Glide.with(activity!!)
            .load(ProdConstants.BASE_URL+"image/"+item.productImage)
            .into(holder.productImage)




        holder.parentView.setOnClickListener {
            activity!!.intent = Intent(activity, ProductDetailActivity::class.java)
            activity.intent.putExtra(Constants.EXTRA_PRODUCTID, item._id)
            activity.startActivity(activity.intent)
        }

    }

    override fun getItemCount(): Int = list.size




    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val parentView = itemView
        val productName = itemView.productName
        val productPrice = itemView.productPrice
        val productImage = itemView.productImage

    }



}