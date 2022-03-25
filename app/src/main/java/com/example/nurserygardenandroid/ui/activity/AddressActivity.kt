package com.example.nurserygardenandroid.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nurserygardenandroid.R
import com.example.nurserygardenandroid.adapter.AddressAdapter
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

class AddressActivity : BaseActivity() {

    val viewModel:AddressViewModel by lazy {
        ViewModelProvider(this).get(AddressViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)
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
                        recyclerView(intent.getBooleanExtra(Constants.EXTRA_SELECT_ADDRESS,false))
                    }else{
                        Toast.makeText(this@AddressActivity, ErrorUtils.errorBody(response.errorBody()!!), Toast.LENGTH_SHORT).show()
                    }

                }

                override fun onFailure(call: Call<UserProfile>, t: Throwable) {
                    Toast.makeText(this@AddressActivity, "Cannot delete...!", Toast.LENGTH_SHORT).show()
                }

            })
    }

    override fun onStart() {
        super.onStart()
        recyclerView(intent.getBooleanExtra(Constants.EXTRA_SELECT_ADDRESS,false))
    }


    fun recyclerView(isAddressSelection:Boolean){

        showProgressBar("Loading address")

        viewModel.refresh(Constants.BEARER+SharedPref(this).getAuthToken())
        viewModel.getAddress.observe(this){response->
            if(response==null){
                Toast.makeText(this, "null response", Toast.LENGTH_SHORT).show()
                return@observe
            }

            dismissProgressBar()

            if(response.isEmpty()){
                setVisibility()
            }
            recycler_view.layoutManager = LinearLayoutManager(this)
            val adapter = AddressAdapter(this, response as ArrayList<Address>, isAddressSelection)
            recycler_view.adapter = adapter
            adapter.notifyDataSetChanged()

        }
    }

    fun setVisibility(){
        emptyAddressHint.visibility = View.VISIBLE
    }


    fun toOrderSummary(address:Address){
        intent = Intent(this, OrderSummaryActivity::class.java)
        intent.putExtra(Constants.PARCELABLE_ADDRESS, address)
        startActivity(intent)
        finish()
    }
}