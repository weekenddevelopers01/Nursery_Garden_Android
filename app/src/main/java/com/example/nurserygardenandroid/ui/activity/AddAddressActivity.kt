package com.example.nurserygardenandroid.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import android.util.Log
import android.widget.Toast
import com.example.nurserygardenandroid.R
import com.example.nurserygardenandroid.model.user.Address
import com.example.nurserygardenandroid.model.user.UserProfile
import com.example.nurserygardenandroid.network.NetworkLayer
import com.example.nurserygardenandroid.sharedpreference.SharedPref
import com.example.nurserygardenandroid.utils.Constants
import kotlinx.android.synthetic.main.activity_add_address.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddAddressActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_address)
    }

    fun back_onclik(view: android.view.View) {
        finish()
    }

    fun add_address_onClick(view: android.view.View) {
        var address = Address(null,
            edit_txt_name.text.toString(),
            edit_txt_address.text.toString(),
            edit_txt_city.text.toString(),
            edit_txt_state.text.toString(),
            edit_txt_zipcode.text.toString(),
            edit_txt_phone.text.toString())

        if(isValid()){
            NetworkLayer.apiClient.addAddress(Constants.BEARER+SharedPref(this).getAuthToken(), address)
                .enqueue(object : Callback<UserProfile>{
                    override fun onResponse(call: Call<UserProfile>, response: Response<UserProfile>) {
                        if (response.isSuccessful){

                            Log.d("responseBody", response.body().toString())
                            Toast.makeText(this@AddAddressActivity, "Saved", Toast.LENGTH_SHORT).show()
                            finish()
                        }else{
                            Toast.makeText(this@AddAddressActivity, "Internal", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<UserProfile>, t: Throwable) {
                        Toast.makeText(this@AddAddressActivity, "error", Toast.LENGTH_SHORT).show()
                    }
                })
        }



    }

    fun isValid():Boolean{
        if(edit_txt_name.text.toString().isNullOrEmpty()){
            showSnackBar("Enter Name", true)
            return false
        }else if(edit_txt_address.text.toString().isNullOrEmpty()){
            showSnackBar("Enter Address", true)
            return false
        }else if(edit_txt_city.text.toString().isNullOrEmpty()){
            showSnackBar("Enter City", true)
            return false
        }else if(edit_txt_state.text.toString().isNullOrEmpty()){
            showSnackBar("Enter State", true)
            return false
        }else if(edit_txt_zipcode.text.toString().isNullOrEmpty()){
            showSnackBar("Enter ZipCode", true)
            return false
        }else if(edit_txt_phone.text.toString().isNullOrEmpty()){
            showSnackBar("Enter Phone", true)
            return false
        }
        return true
    }
}