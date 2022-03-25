package com.example.nurserygardenandroid.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nurserygardenandroid.R
import com.example.nurserygardenandroid.adapter.ProductSummaryAdapter
import com.example.nurserygardenandroid.model.order.Order
import com.example.nurserygardenandroid.model.order.OrderItem
import com.example.nurserygardenandroid.model.products.Products
import com.example.nurserygardenandroid.model.user.Address
import com.example.nurserygardenandroid.network.NetworkLayer
import com.example.nurserygardenandroid.sharedpreference.SharedPref
import com.example.nurserygardenandroid.utils.Constants
import com.example.nurserygardenandroid.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.activity_order_summary.*
import kotlinx.android.synthetic.main.activity_order_summary.deliveryCharge
import kotlinx.android.synthetic.main.activity_order_summary.grandTotal
import kotlinx.android.synthetic.main.activity_order_summary.total
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderSummaryActivity : BaseActivity() {


    private lateinit var list: List<Products>

    val viewModel: ProductViewModel by lazy{
        ViewModelProvider(this).get(ProductViewModel::class.java)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_summary)

        viewModel.getCartlist(Constants.BEARER+ SharedPref(this).getAuthToken())
        viewModel.getProducts.observe(this){response->
            if(response==null){
                Toast.makeText(this, "Error while loading", Toast.LENGTH_SHORT).show()
                return@observe
            }
            list=response

            if(response.isEmpty()){

            }
            val adapter = ProductSummaryAdapter(this, response)
            orderlist.layoutManager = LinearLayoutManager(this)
            orderlist.adapter = adapter

//            dismissProgressBar()

        }
        setAddress()


    }

    fun setAddress(){
        var address = intent.getParcelableExtra<Address>(Constants.PARCELABLE_ADDRESS)
        name.setText(address?.name)
        summary_address.setText(address?.address)
        summary_city.setText(address?.city)
        summary_state_zipcode.setText(address?.state+" "+address?.zipcode)
        summary_phone.setText(address?.phone)
    }


    fun totalPriceCallBack(price: Double, flag: Boolean){
        total.setText(price.toString())
        deliveryCharge.setText("100")
        var gTotal = price+100
        grandTotal.setText(String.format("%.2f",gTotal))
        totalSummary.setText(String.format("%.2f",gTotal))
    }

    fun back_onclik(view: android.view.View) {finish()}

    fun btn_confirm_order(view: android.view.View) {
        val orderItem: ArrayList<OrderItem> = ArrayList()

        Toast.makeText(this, "yes here", Toast.LENGTH_SHORT).show()

        for(i in list){
            var item = OrderItem(null, null, i._id, null, null, i.qty, null)
            orderItem.add(item)
        }

        var address = intent.getParcelableExtra<Address>(Constants.PARCELABLE_ADDRESS)
        var orderPayload = Order(null,
            null,
            null,
            null,
            null,
            null,
            orderItem,
            null,
            "Order placed",
            false,
            false,
            null ,
            address?._id!! )

        placeOrder(orderPayload)

    }

    fun placeOrder(order:Order){
            NetworkLayer.apiClient.placeOrder(Constants.BEARER+ SharedPref(this).getAuthToken(), order)
                .enqueue(object : Callback<Order>{
                    override fun onResponse(call: Call<Order>, response: Response<Order>) {
                        if(response.isSuccessful){
                            orderPlaced()
                        }
                    }

                    override fun onFailure(call: Call<Order>, t: Throwable) {
                    }

                })
    }

    fun orderPlaced(){
        intent = Intent(this, HomeActivity::class.java)
        intent?.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}