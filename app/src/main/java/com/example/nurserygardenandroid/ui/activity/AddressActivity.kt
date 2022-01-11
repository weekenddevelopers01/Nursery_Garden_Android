package com.example.nurserygardenandroid.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nurserygardenandroid.R
import com.example.nurserygardenandroid.adapter.AddressAdapter
import com.example.nurserygardenandroid.model.ProductViewModel
import com.example.nurserygardenandroid.model.user.Address
import com.example.nurserygardenandroid.model.user.UserProfile
import com.example.nurserygardenandroid.network.NetworkLayer
import com.example.nurserygardenandroid.sharedpreference.SharedPref
import com.example.nurserygardenandroid.utils.Constants
import com.example.nurserygardenandroid.utils.ErrorUtils
import com.example.nurserygardenandroid.viewmodel.AddressViewModel
import kotlinx.android.synthetic.main.activity_address.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddressActivity : AppCompatActivity() {

    val viewModel:AddressViewModel by lazy {
        ViewModelProvider(this).get(AddressViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)

        recyclerView()
    }

    fun back_onclik(view: android.view.View) {
        finish()
    }

    fun add_address_onClick(view: android.view.View) {
        intent = Intent(this, AddAddressActivity::class.java)
        startActivity(intent)
    }


    open fun deleteAddress(_id:String){
        NetworkLayer.apiClient.deleteAddress(Constants.BEARER+SharedPref(this).getAuthToken(), _id)
            .enqueue(object : Callback<UserProfile>{
                override fun onResponse(call: Call<UserProfile>, response: Response<UserProfile>) {
                    if(response.isSuccessful){
                        recyclerView()
                    }else{
                        Toast.makeText(this@AddressActivity, ErrorUtils.errorBody(response.errorBody()!!), Toast.LENGTH_SHORT).show()
                    }

                }

                override fun onFailure(call: Call<UserProfile>, t: Throwable) {
                    Toast.makeText(this@AddressActivity, "Cannot delete...!", Toast.LENGTH_SHORT).show()
                }

            })
    }


    fun recyclerView(){
        viewModel.refresh(Constants.BEARER+SharedPref(this).getAuthToken())
        viewModel.getAddres.observe(this){response->
            if(response==null){
                Toast.makeText(this, "null response", Toast.LENGTH_SHORT).show()
                return@observe
            }

            recycler_view.layoutManager = LinearLayoutManager(this)
            val adapter = AddressAdapter(this, response as ArrayList<Address>)
            recycler_view.adapter = adapter
            adapter.notifyDataSetChanged()

        }
    }
}