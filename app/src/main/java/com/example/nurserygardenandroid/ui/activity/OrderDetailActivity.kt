package com.example.nurserygardenandroid.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nurserygardenandroid.R
import com.example.nurserygardenandroid.adapter.OrderSummaryAdapter
import com.example.nurserygardenandroid.model.order.Order
import com.example.nurserygardenandroid.network.NetworkLayer
import com.example.nurserygardenandroid.sharedpreference.SharedPref
import com.example.nurserygardenandroid.utils.Constants
import com.example.nurserygardenandroid.utils.ErrorUtils
import kotlinx.android.synthetic.main.activity_order_detail.*
import kotlinx.android.synthetic.main.order_item.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderDetailActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)


        setView()

    }

    private fun setView() {
        showProgressBar("Loading...!")
        val id = intent.getStringExtra(Constants.EXTRA_ORDER_ID)
        NetworkLayer.apiClient.getOrder(Constants.BEARER+ SharedPref(this).getAuthToken()
            , id).enqueue(object : Callback<Order>{
            override fun onResponse(call: Call<Order>, response: Response<Order>) {
                if(response.isSuccessful){

                    var fI = response.body()?.date!!.indexOf("T");
                    order_id1.text = response.body()?.invoiceNo
                    order_date1.text = response.body()?.date!!.substring(0,fI);
                    order_status1.text = response.body()?.status

                    order_total.text = response.body()?.grandTotal.toString()
                    order_discount.text ="0"
                    order_deliveryCharge.text ="0"
                    order_grandTotal.text = response.body()?.grandTotal.toString()

                    if (response.body()!!.isDelivered){
                        order_description.text ="Order Delivered"
                        cancel_order.visibility = View.GONE

                    }
                    if(response.body()!!.isCancelled){
                        cancel_order.visibility = View.GONE
                        order_description.text ="Order Cancelled"
                    }

                    orderSummaryRecyclerView.layoutManager = LinearLayoutManager(this@OrderDetailActivity)
                    var adapter = OrderSummaryAdapter(this@OrderDetailActivity, response.body()!!.orderItems)
                    orderSummaryRecyclerView.adapter = adapter

                }else{
                    showSnackBar(ErrorUtils.errorBody(response.errorBody()!!), true)
                }
                dismissProgressBar()
            }

            override fun onFailure(call: Call<Order>, t: Throwable) {

                dismissProgressBar()
            }

        })

    }

    fun back_onclik(view: View) {
        finish()
    }

    fun cancelOrderOnClick(view: View) {

        showProgressBar("Loading...!")
        val id = intent.getStringExtra(Constants.EXTRA_ORDER_ID)
        NetworkLayer.apiClient.cancelOrder(Constants.BEARER+ SharedPref(this).getAuthToken()
            , id)
            .enqueue(object :Callback<Order>{
                override fun onResponse(call: Call<Order>, response: Response<Order>) {
                    if (response.isSuccessful){
                        order_status1.text = response.body()!!.status


                        if(response.body()!!.isCancelled){
                            cancel_order.visibility = View.GONE
                            order_description.text ="Order Cancelled"
                        }

                        dismissProgressBar()
                    }else{
                        showSnackBar(ErrorUtils.errorBody(response.errorBody()!!), true)
                        dismissProgressBar()
                    }


                }

                override fun onFailure(call: Call<Order>, t: Throwable) {
                    dismissProgressBar()
                }

            })
    }


}