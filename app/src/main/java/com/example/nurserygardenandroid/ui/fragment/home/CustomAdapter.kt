package com.example.nurserygardenandroid.ui.fragment.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.nurserygardenandroid.ui.activity.ProductDetailActivity
import com.example.nurserygardenandroid.R
import com.example.nurserygardenandroid.model.Question
import kotlinx.android.synthetic.main.product_list_item.view.*

class CustomAdapter(val activity: FragmentActivity?, val list: ArrayList<Question>): RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapter.ViewHolder {
        val v  = LayoutInflater.from(parent.context).inflate(R.layout.product_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val item = list.get(position)
//        holder.name.text = item.name
//        holder.price.text = item.price.toString()
        holder.itemView.setOnClickListener({
            activity?.intent = Intent(activity, ProductDetailActivity::class.java)
            activity?.startActivity(activity?.intent)
        })
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemCount() = list.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val name = itemView.name
        val price = itemView.price

    }
}