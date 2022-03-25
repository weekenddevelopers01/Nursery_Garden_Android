package com.example.nurserygardenandroid.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.BoringLayout
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nurserygardenandroid.R
import com.example.nurserygardenandroid.adapter.CartListAdapter
import com.example.nurserygardenandroid.sharedpreference.SharedPref
import com.example.nurserygardenandroid.utils.Constants
import com.example.nurserygardenandroid.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_wish_list.*
import kotlinx.android.synthetic.main.activity_wish_list.recycler_view

class CartActivity : BaseActivity() {

    val viewModel: ProductViewModel by lazy{
        ViewModelProvider(this).get(ProductViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        recyclerView()
    }

    fun back_onclik(view: android.view.View) {
        finish()
    }

    var isEliglible:Boolean = true

    fun totalPriceCallBack(price: Double, flag: Boolean){
        total.setText(price.toString())
        deliveryCharge.setText("100")
        var gTotal = price+100
        grandTotal.setText(String.format("%.2f",gTotal))
        totalBanner.setText(String.format("%.2f",gTotal))
        isEliglible=flag
        if(!flag){

            showSnackBar("One of item Out of stock",false)
        }

    }



    private fun recyclerView(){
        showProgressBar("Loading your Cart...!")
        viewModel.getCartlist(Constants.BEARER+ SharedPref(this).getAuthToken())
        viewModel.getProducts.observe(this){response->
            if(response==null){
                Toast.makeText(this, "Error while loading", Toast.LENGTH_SHORT).show()
                return@observe
            }
            if(response.isEmpty()){
                setVisibility()
            }
            recycler_view.layoutManager = LinearLayoutManager(this)
            val adapter = CartListAdapter(this, response)
            recycler_view.adapter = adapter

            dismissProgressBar()
        }
    }

    fun setVisibility(){
        priceDetailContainer.visibility = View.INVISIBLE
        emptyCartHint.visibility = View.VISIBLE
        footer.visibility= View.INVISIBLE
    }

    fun placeOrder(view: android.view.View) {
        if(!isEliglible){
            showSnackBar("One of item Out of stock",false)
        }else{
            intent = Intent(this, AddressActivity::class.java)
            intent.putExtra(Constants.EXTRA_SELECT_ADDRESS, true)
            startActivity(intent)
        }
    }

    fun cartEmptyCallback(size:Int){
       if(size<=0){
           Toast.makeText(this, "Your cart is Empty", Toast.LENGTH_SHORT).show()
           finish()
       }
    }


}