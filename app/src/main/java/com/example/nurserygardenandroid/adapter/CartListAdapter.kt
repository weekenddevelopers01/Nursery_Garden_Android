package com.example.nurserygardenandroid.adapter

import android.content.Context
import android.graphics.Color
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nurserygardenandroid.R
import com.example.nurserygardenandroid.model.products.Products
import com.example.nurserygardenandroid.model.user.UserProfile
import com.example.nurserygardenandroid.network.NetworkLayer
import com.example.nurserygardenandroid.sharedpreference.SharedPref
import com.example.nurserygardenandroid.ui.activity.CartActivity
import com.example.nurserygardenandroid.utils.Constants
import com.example.nurserygardenandroid.utils.ProdConstants
import kotlinx.android.synthetic.main.cartlist_item.view.*
import kotlinx.coroutines.channels.consumesAll
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartListAdapter(val context:Context, val list:List<Products>): RecyclerView.Adapter<CartListAdapter.ViewHolder>() {


    private var listDatas: MutableList<Products> = list as MutableList<Products>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val v = LayoutInflater.from(context).inflate(R.layout.cartlist_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var listData = listDatas.get(position)

        if(!listData.status){
            holder.qtyContainer.visibility = View.INVISIBLE
            holder.proudctPrice.setText("Out Of Stock")
            holder.proudctPrice.setTextColor(Color.RED)
        }else{
            var total = listData.productPrice*listData.qty
            holder.proudctPrice.setText(total.toString())
            holder.proudctPrice.setTextColor(Color.BLACK)
        }

        holder.productQty.setText(listData.qty.toString())
        holder.productName.setText(listData.productName)

//        val imageBytes = Base64.decode(listData.image, Base64.DEFAULT)
        Glide.with(context).load(ProdConstants.BASE_URL+"image/"+listData.productImage).into(holder.productImage)

        holder.deleteProduct.setOnClickListener{
            NetworkLayer.apiClient.removeCartItem(Constants.BEARER+ SharedPref(context).getAuthToken(), listData._id)
                .enqueue(object : Callback<UserProfile>{
                    override fun onResponse(
                        call: Call<UserProfile>,
                        response: Response<UserProfile>
                    ) {
                        if(response.isSuccessful){
                            deleteItem(holder.adapterPosition)
//                            notifyItemRemoved(holder.adapterPosition)
                        }else{
                            Toast.makeText(context, "Can't remove ", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<UserProfile>, t: Throwable) {
                        Toast.makeText(context, "Internal server Error", Toast.LENGTH_SHORT).show()
                    }

                })
        }

        holder.addProduct.setOnClickListener{


            var activity = context as CartActivity

            activity.showProgressBar("Adding Product")

            NetworkLayer.apiClient.addRemoveQty(Constants.BEARER+ SharedPref(context).getAuthToken(),"add", listData._id)
                .enqueue(object : Callback<Products>{
                    override fun onResponse(call: Call<Products>, response: Response<Products>) {
                        if(response.isSuccessful){

                            Log.d("error->","error pa")
                            activity.dismissProgressBar()
                            list.get(holder.adapterPosition).qty++;
//                            notifyItemChanged(holder.adapterPosition, response.body())
                            notifyDataSetChanged()



                        }else{
                            activity.dismissProgressBar()
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()

                        }
                        activity.dismissProgressBar()
                    }

                    override fun onFailure(call: Call<Products>, t: Throwable) {
                        activity.dismissProgressBar()
                        Toast.makeText(context, "Internal server error", Toast.LENGTH_SHORT).show()
                        activity.dismissProgressBar()
                    }

                })


        }

        holder.removeProduct.setOnClickListener{

            var activity = context as CartActivity

            activity.showProgressBar("Removing Product")

            NetworkLayer.apiClient.addRemoveQty(Constants.BEARER+ SharedPref(context).getAuthToken(),"remove", listData._id)
                .enqueue(object : Callback<Products>{
                    override fun onResponse(call: Call<Products>, response: Response<Products>) {
                        if(response.isSuccessful){
                            activity.dismissProgressBar()
                            list.get(holder.adapterPosition).qty--
                            notifyDataSetChanged()

//                            notifyItemChanged(holder.adapterPosition)

//                            notifyItemChanged(holder.adapterPosition, response.body())
                        }else{
                            activity.dismissProgressBar()
                            Toast.makeText(context, "cannot", Toast.LENGTH_SHORT).show()

                        }
                    }

                    override fun onFailure(call: Call<Products>, t: Throwable) {
                        activity.dismissProgressBar()
                        Toast.makeText(context, "cannot", Toast.LENGTH_SHORT).show()

                    }

                })

        }

        calculate()
    }


    private fun calculate(){
        var total = 0.0
        var flag = true
        for(p in listDatas){
            var subTotal = p.qty * p.productPrice
            total += subTotal
            if(!p.status){
                flag=false
            }
        }

        var activity = context as CartActivity
        activity.totalPriceCallBack(total, flag)
    }


    fun deleteItem(index: Int){

        listDatas.removeAt(index)
        (context as CartActivity).cartEmptyCallback(listDatas.size)
        notifyItemRemoved(index)
        calculate()
//        notifyDataSetChanged()
    }



    override fun getItemCount(): Int = listDatas.size
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val productImage = itemView.cartItemImage
        val proudctPrice = itemView.cartItemProductPrice
        val productName = itemView.cartItemProductName
        val productQty = itemView.qty
        val addProduct = itemView.addProduct
        val removeProduct = itemView.removeProduct
        val deleteProduct = itemView.deleteProduct
        val qtyContainer = itemView.qtyContainer
    }


}