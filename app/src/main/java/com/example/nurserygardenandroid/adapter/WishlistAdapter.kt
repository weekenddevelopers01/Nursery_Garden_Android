package com.example.nurserygardenandroid.adapter

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nurserygardenandroid.R
import com.example.nurserygardenandroid.model.products.Products
import com.example.nurserygardenandroid.ui.activity.WishListActivity
import com.example.nurserygardenandroid.utils.ProdConstants
import kotlinx.android.synthetic.main.wishlist_item.view.*

class WishlistAdapter(val context: Context, val list:List<Products>):
    RecyclerView.Adapter<WishlistAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v = LayoutInflater.from(context).inflate(R.layout.wishlist_item,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list.get(position)
        holder.productName.setText(item.productName)
        holder.productPrice.setText(item.productPrice.toString())
//        val imageBytes = Base64.decode(item.image, Base64.DEFAULT)
        Glide.with(context).load(ProdConstants.BASE_URL+"image/"+item.productImage).into(holder.productImage)

        if(list[position].qty<=0){
            holder.moveToCart.text="Out of Stock"
            holder.moveToCart.setTextColor(Color.BLACK)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.moveToCart.background=context.getDrawable(R.drawable.round_btn_fadeout_bg)
            };
        }else{
            holder.moveToCart.setOnClickListener {
                val c = context as WishListActivity
                c.wishlistToCartCallBack(item._id)
            }
        }

        holder.deleteFromCart.setOnClickListener{
            val c=context as WishListActivity
            c.removeFromWishlistCallBack(item._id)
        }


    }

    override fun getItemCount(): Int =list.size

    class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){

        val productImage = itemView.productImage
        val productName = itemView.productName
        val productPrice = itemView.productPrice
        val moveToCart = itemView.moveTocart
        val deleteFromCart = itemView.deleteFromCart
    }
}