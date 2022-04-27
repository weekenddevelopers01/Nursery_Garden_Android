package com.example.nurserygardenandroid.ui.activity

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nurserygardenandroid.R
import com.example.nurserygardenandroid.adapter.OrderAdapter
import com.example.nurserygardenandroid.model.order.Order
import com.example.nurserygardenandroid.network.NetworkLayer
import com.example.nurserygardenandroid.sharedpreference.SharedPref
import com.example.nurserygardenandroid.utils.Constants
import com.example.nurserygardenandroid.utils.ErrorUtils
import kotlinx.android.synthetic.main.activity_order.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)


//        setRecyclerView()
//        val imageBytes = Base64.decode(bufferStr, Base64.DEFAULT)
//        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
//        bufferImage.setImageBitmap(decodedImage)

    }

    fun back_onclik(view: android.view.View) {
        finish()
    }

    fun setRecyclerView(){

        showProgressBar("Loading Orders")
        NetworkLayer.apiClient.getOrderList(Constants.BEARER+ SharedPref(this).getAuthToken())
            .enqueue(object : Callback<List<Order>>{
                override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                    if (response.isSuccessful){
                        orderRecyclerview.layoutManager = LinearLayoutManager(this@OrderActivity)
                        val adapter = OrderAdapter(this@OrderActivity, response.body()!!)
                        orderRecyclerview.adapter = adapter

                    }else{
                        showSnackBar(ErrorUtils.errorBody(response.errorBody()!!), true)
                    }

                    dismissProgressBar()
                }

                override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                    dismissProgressBar()

                }


            })

    }

    override fun onResume() {
        super.onResume()
        setRecyclerView()
    }



}