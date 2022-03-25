package com.example.nurserygardenandroid.ui.activity

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.nurserygardenandroid.R
import com.example.nurserygardenandroid.adapter.ProductAdapter
import com.example.nurserygardenandroid.adapter.WishlistAdapter
import com.example.nurserygardenandroid.model.Question
import com.example.nurserygardenandroid.model.user.UserProfile
import com.example.nurserygardenandroid.model.user.WishListItem
import com.example.nurserygardenandroid.network.NetworkLayer
import com.example.nurserygardenandroid.sharedpreference.SharedPref
import com.example.nurserygardenandroid.ui.fragment.home.CustomAdapter
import com.example.nurserygardenandroid.utils.Constants
import com.example.nurserygardenandroid.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.activity_wish_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WishListActivity : BaseActivity() {

    val viewModel: ProductViewModel by lazy{
        ViewModelProvider(this).get(ProductViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wish_list)


        recyclerView()



    }


    private fun recyclerView(){
        showProgressBar("Loading...!")
        viewModel.getWishList(Constants.BEARER+SharedPref(this).getAuthToken())
        viewModel.getProducts.observe(this){response->
            if(response==null){
                Toast.makeText(this, "Error while loading", Toast.LENGTH_SHORT).show()
                return@observe
            }
            if(response.isEmpty()){
                setVisibility()
            }
            dismissProgressBar()
            recycler_view.layoutManager = GridLayoutManager(this@WishListActivity, 2)
            val adapter = WishlistAdapter(this, response)
            recycler_view.adapter = adapter
        }
    }

    fun setVisibility(){
        emptyWishListHint.visibility = View.VISIBLE
    }



     open fun wishlistToCartCallBack(item:String){
        NetworkLayer.apiClient.wishlistToCart(Constants.BEARER+SharedPref(this).getAuthToken(), WishListItem(item))
            .enqueue(object : Callback<UserProfile>{
                override fun onResponse(call: Call<UserProfile>, response: Response<UserProfile>) {
                    if(response.isSuccessful){
                        recyclerView()
                        Toast.makeText(this@WishListActivity, "Item Moved to Cart", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this@WishListActivity, "Cannot move", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<UserProfile>, t: Throwable) {
                    Toast.makeText(this@WishListActivity, t.message, Toast.LENGTH_SHORT).show()
                }

            })
    }


    open fun removeFromWishlistCallBack(item:String){
        NetworkLayer.apiClient.removeFromWishlist(Constants.BEARER+SharedPref(this).getAuthToken(), item)
            .enqueue(object : Callback<UserProfile>{
                override fun onResponse(call: Call<UserProfile>, response: Response<UserProfile>) {
                    if(response.isSuccessful){
                        recyclerView()
                        Toast.makeText(this@WishListActivity, "Removed", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this@WishListActivity, "Cannot Remove", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<UserProfile>, t: Throwable) {
                    Toast.makeText(this@WishListActivity, t.message, Toast.LENGTH_SHORT).show()
                }

            })
    }


    fun back_onclik(view: android.view.View) {
        finish()
    }


}