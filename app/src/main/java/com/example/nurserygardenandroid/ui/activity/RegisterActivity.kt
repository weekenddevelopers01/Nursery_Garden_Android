package com.example.nurserygardenandroid.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.nurserygardenandroid.R
import com.example.nurserygardenandroid.utils.ErrorUtils
import com.example.nurserygardenandroid.model.user.UserAuth
import com.example.nurserygardenandroid.model.user.UserLogin
import com.example.nurserygardenandroid.network.NetworkLayer
import com.example.nurserygardenandroid.sharedpreference.SharedPref
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : BaseActivity() {

    var emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    fun btn_register_onClick(view: View) {

        if(isValid()){
           showProgressBar("Creating.....!")
            var newUser = UserLogin(edit_txt_emil.text.toString(), edit_txt_password.text.toString())
            NetworkLayer.apiClient.createUser(newUser).enqueue(object : Callback<UserAuth>{
                override fun onResponse(call: Call<UserAuth>, response: Response<UserAuth>) {

                    if(response.isSuccessful){
                        Toast.makeText(baseContext, ""+response.body(), Toast.LENGTH_SHORT).show()
                        SharedPref(baseContext).setAuthToken(response.body()?.token!!)
                        SharedPref(baseContext).setIsProfiled(false)
                        dismissProgressBar()
                        finish()
                    }else{
                        showSnackBar(ErrorUtils.errorBody(response.errorBody()!!), true)
                    }

                }

                override fun onFailure(call: Call<UserAuth>, t: Throwable) {
                    Toast.makeText(baseContext, "Failed to create Account", Toast.LENGTH_SHORT).show()
                    dismissProgressBar()
                }

            })
        }



    }


    private fun isValid(): Boolean {
        var email = edit_txt_emil.text
        var password = edit_txt_password.text
        var cPassword = edit_txt_confirm_password.text

        if(email.isNullOrEmpty() || password.isNullOrEmpty() || cPassword.isNullOrEmpty()){
            showSnackBar("Fill All details", false)
            return  false
        }else if(!email.toString().trim(){it<= ' '}.matches(emailPattern.toRegex())){
            showSnackBar("Invalid Email Entered", true)
            return false
        }else if(!password.contentEquals(cPassword)){
            showSnackBar("Password not matching", true)
            return false
        }
        return true
    }

    fun back_onclik(view: android.view.View) {
        finish();
    }
}