package com.example.nurserygardenandroid.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import android.text.Editable
import android.util.Log
import android.widget.Toast
import com.example.nurserygardenandroid.R
import com.example.nurserygardenandroid.model.user.UserProfile
import com.example.nurserygardenandroid.network.NetworkLayer
import com.example.nurserygardenandroid.sharedpreference.SharedPref
import com.example.nurserygardenandroid.utils.Constants
import com.example.nurserygardenandroid.utils.ErrorUtils
import kotlinx.android.synthetic.main.activity_edit_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProfileActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        updateViews()
    }

    fun updateViews(){
        showProgressBar("Loading Profile")

        NetworkLayer.apiClient.getUserProfile(Constants.BEARER+SharedPref(this).getAuthToken())
            .enqueue(object : Callback<UserProfile>{
                override fun onResponse(call: Call<UserProfile>, response: Response<UserProfile>) {
                    dismissProgressBar()
                    if(response.isSuccessful){
                        var user = response.body()
                        edit_txt_fname.setText(user?.f_name)
                        edit_txt_lname.setText(user?.l_name)
                        edit_txt_emil.setText(user?.email)
                        edit_txt_phone.setText(user?.phone)
                    }else{
                        showSnackBar(ErrorUtils.errorBody(response.errorBody()!!), true)
                    }
                }

                override fun onFailure(call: Call<UserProfile>, t: Throwable) {
                    Toast.makeText(baseContext, "result "+t.message, Toast.LENGTH_SHORT).show()
                    dismissProgressBar()
                }

            })
    }

    fun back_onclik(view: android.view.View) {
      finish()
    }

    fun updateUserProfile(){
        showProgressBar("Updating Profile..!")

        var map = HashMap<String,Any>()
        map[Constants.USERPROFILE_FNAME] = edit_txt_fname.text.toString().trim()
        map[Constants.USERPROFILE_LNAME] = edit_txt_lname.text.toString().trim()
        map[Constants.USERPROFILE_EMAIL] = edit_txt_emil.text.toString().trim()
        map[Constants.USERPROFILE_PHONE] = edit_txt_phone.text.toString().trim()

        Log.d("Email" ,edit_txt_emil.text.toString())


        NetworkLayer.apiClient.updateUserProfile(Constants.BEARER+SharedPref(this).getAuthToken(),
        map).enqueue(object : Callback<UserProfile>{
            override fun onResponse(call: Call<UserProfile>, response: Response<UserProfile>) {
                dismissProgressBar()
                if(response.isSuccessful){
                    Toast.makeText(this@EditProfileActivity, "Profile Updated", Toast.LENGTH_SHORT).show()
                    var fullName :String = map[Constants.USERPROFILE_FNAME] as String +" "+ map[Constants.USERPROFILE_LNAME] as String
                    SharedPref(this@EditProfileActivity)
                        .setUserDeails(fullName, map[Constants.USERPROFILE_PHONE] as String, map[Constants.USERPROFILE_EMAIL]as String)
                    finish()
                }else{
                    showSnackBar(ErrorUtils.errorBody(response.errorBody()!!), true)
                }
            }

            override fun onFailure(call: Call<UserProfile>, t: Throwable) {
                dismissProgressBar()
                Toast.makeText(this@EditProfileActivity, "Error "+t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun save_onclik(view: android.view.View) {
        updateUserProfile()
    }
}