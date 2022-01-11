package com.example.nurserygardenandroid.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.nurserygardenandroid.R
import com.example.nurserygardenandroid.model.*
import com.example.nurserygardenandroid.model.user.UserAuth
import com.example.nurserygardenandroid.model.user.UserLogin
import com.example.nurserygardenandroid.network.NetworkLayer
import com.example.nurserygardenandroid.repository.ProductRepository
import com.example.nurserygardenandroid.sharedpreference.SharedPref
import com.example.nurserygardenandroid.utils.Constants
import com.example.nurserygardenandroid.utils.ErrorUtils
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response

class MainActivity : BaseActivity() {


    val viewModel:ProductViewModel by lazy {
        ViewModelProvider(this).get(ProductViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        viewModel.refresh()
//        viewModel.getProducts.observe(this){response->
//            if(response==null) {
//
//                Toast.makeText(baseContext, "Null in response", Toast.LENGTH_SHORT).show()
//                return@observe
//            }
//
//            Toast.makeText(baseContext, "size "+response.size, Toast.LENGTH_SHORT).show()
//
//        }

    }

    fun register_onClick(view: View) {
        intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    fun btn_login_onClick(view: View) {

        if(isValid()){
            showProgressBar("Please wait..!")

            var repository: ProductRepository = ProductRepository()

            var userLogin = UserLogin(edit_txt_emil.text.toString().trim(),edit_txt_password.text.toString().trim())

            NetworkLayer.apiClient.loginUser(userLogin).enqueue(object : retrofit2.Callback<UserAuth>{
                override fun onResponse(call: Call<UserAuth>, response: Response<UserAuth>) {

                    if(response.isSuccessful){
                        dismissProgressBar()
                        SharedPref(baseContext).setAuthToken(response.body()?.token)
                        SharedPref(baseContext).setIsProfiled(response.body()?.auth?.isProfiled!!)
                        Log.d("is profiled", response.body()?.auth?.isProfiled.toString())
                        next(response.body()!!)
                    }else if(response.code()==404){
                        dismissProgressBar()
                        showSnackBar(ErrorUtils.errorBody(response.errorBody()!!), true)
                    }
                }
                override fun onFailure(call: Call<UserAuth>, t: Throwable) {
                    Toast.makeText(baseContext, "result "+t.message, Toast.LENGTH_SHORT).show()
                    dismissProgressBar()
                }
            })

        }else{
            showSnackBar("Please Enter Email & Password", true)
        }


    }


    private fun next(userAuth: UserAuth){

        if(userAuth.auth!!.isProfiled){
            intent = Intent(this, HomeActivity::class.java)
            intent?.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent?.putExtra(Constants.PARCELABLE_USER, userAuth)
            startActivity(intent)
        }else{
            intent = Intent(this, CompleteProfileActivity::class.java)
            intent?.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent?.putExtra(Constants.PARCELABLE_USER, userAuth)
            startActivity(intent)
        }

    }

    fun isValid():Boolean{

        if(edit_txt_emil.text.isNullOrEmpty() || edit_txt_password.text.isNullOrEmpty()){
            return false
        }

        return true
    }

    override fun onStart() {
        super.onStart()
        if(!SharedPref(baseContext).getAuthToken().isNullOrEmpty() && !SharedPref(baseContext).getIsProfiled()){
            intent = Intent(this, CompleteProfileActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }else if(!SharedPref(baseContext).getAuthToken().isNullOrEmpty() && SharedPref(baseContext).getIsProfiled()){
            intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}