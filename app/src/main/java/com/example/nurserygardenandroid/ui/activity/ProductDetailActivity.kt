package com.example.nurserygardenandroid.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import android.util.Base64
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.nurserygardenandroid.R
import com.example.nurserygardenandroid.model.products.Products
import com.example.nurserygardenandroid.model.user.CartItem
import com.example.nurserygardenandroid.model.user.UserProfile
import com.example.nurserygardenandroid.model.user.WishListItem
import com.example.nurserygardenandroid.network.NetworkLayer
import com.example.nurserygardenandroid.sharedpreference.SharedPref
import com.example.nurserygardenandroid.utils.Constants
import com.example.nurserygardenandroid.utils.ErrorUtils
import com.example.nurserygardenandroid.utils.ProdConstants
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.activity_product_detail.productName
import kotlinx.android.synthetic.main.activity_product_detail.productPrice
import kotlinx.android.synthetic.main.product_list_item.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductDetailActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_product_detail)

        var productID: String? = intent.getStringExtra(Constants.EXTRA_PRODUCTID)
        loadViews(productID!!)

    }

    private fun loadViews(id:String){
        showProgressBar("")
        NetworkLayer.apiClient.getSelectedProduct(id).enqueue(object : Callback<Products>{
            override fun onResponse(call: Call<Products>, response: Response<Products>) {
                if(response.isSuccessful){
                    productRating.setText(response.body()?.rating.toString())
                    productLife.setText(response.body()?.life)
                    productHeight.setText(response.body()?.height.toString())
                    productWeight.setText(response.body()?.weight.toString())
                    productName.setText(response.body()?.productName)
                    productPrice.setText("Rs "+response.body()?.productPrice.toString())
                    productDescription.setText(response.body()?.description)
//                    val imageBytes = Base64.decode(response.body()?.image, Base64.DEFAULT)
                    Glide.with(this@ProductDetailActivity)
                        .load(ProdConstants.BASE_URL+"image/"+response.body()?.productImage)
                        .into(pImage)

                }else{
                    showSnackBar(ErrorUtils.errorBody(response.errorBody()!!), true)
                }

                dismissProgressBar()
            }

            override fun onFailure(call: Call<Products>, t: Throwable) {
                dismissProgressBar()
                Toast.makeText(this@ProductDetailActivity, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun cartOnclick(item: android.view.MenuItem) {
        if(item.itemId == R.id.cart){
            var intent = Intent(this, ProductDetailActivity::class.java)
            startActivity(intent)
        }
    }

    fun back_onclik(view: android.view.View) {
        finish()

    }

    fun addToCart(view: android.view.View) {
        var cartItem = CartItem(intent.getStringExtra(Constants.EXTRA_PRODUCTID)!!, 1)

        NetworkLayer.apiClient.addCartItem(Constants.BEARER+ SharedPref(this).getAuthToken(), cartItem)
            .enqueue(object :Callback<UserProfile>{
                override fun onResponse(call: Call<UserProfile>, response: Response<UserProfile>) {
                    if (response.isSuccessful){
                        showSnackBar("Added to Cart", false);
                    }else{
                        showSnackBar(ErrorUtils.errorBody(response.errorBody()!!), true)
                    }
                }

                override fun onFailure(call: Call<UserProfile>, t: Throwable) {

                    showSnackBar(""+t.message,false)
                }

            })

    }


    fun addToWishList(view : android.view.View){
        var wishListItem = WishListItem(intent.getStringExtra(Constants.EXTRA_PRODUCTID)!!)

        NetworkLayer.apiClient.addToWishlist(Constants.BEARER+ SharedPref(this).getAuthToken(), wishListItem)
            .enqueue(object :Callback<UserProfile>{
                override fun onResponse(call: Call<UserProfile>, response: Response<UserProfile>) {
                    Log.d("Cart item", "entered heer"+response.body())
                    if(response.isSuccessful){
                        showSnackBar("added to cart", true)
                    }else{
                        showSnackBar(ErrorUtils.errorBody(response.errorBody()!!), true)
                    }
                }

                override fun onFailure(call: Call<UserProfile>, t: Throwable) {

                    Toast.makeText(this@ProductDetailActivity, t.message, Toast.LENGTH_SHORT).show()
                }

            })
    }
}

