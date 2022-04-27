package com.example.nurserygardenandroid.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.nurserygardenandroid.R
import com.example.nurserygardenandroid.model.user.UserAuth
import com.example.nurserygardenandroid.model.user.UserProfile
import com.example.nurserygardenandroid.network.NetworkLayer
import com.example.nurserygardenandroid.sharedpreference.SharedPref
import com.example.nurserygardenandroid.utils.Constants
import com.example.nurserygardenandroid.utils.ErrorUtils
import kotlinx.android.synthetic.main.activity_complete_profile.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class CompleteProfileActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complete_profile)
    }

    fun btn_create_profile_onClick(view: android.view.View) {

        var map = HashMap<String, Any>()
        map[Constants.USERPROFILE_FNAME] = edit_txt_fname.text.toString()
        map[Constants.USERPROFILE_LNAME] = edit_txt_lname.text.toString()
        map[Constants.USERPROFILE_EMAIL] = edit_txt_emil.text.toString()
        map[Constants.USERPROFILE_ISVERIFIED] = true;
        map[Constants.USERPROFILE_PHONE] = edit_txt_phone.text.toString()

        showProgressBar("Creating....!")

        NetworkLayer.apiClient.createUserProfile(map,
            Constants.BEARER+SharedPref(baseContext).getAuthToken()).enqueue(object : retrofit2.Callback<UserProfile>{
            override fun onResponse(call: Call<UserProfile>, response: Response<UserProfile>) {
                if(response.isSuccessful){
                    SharedPref(baseContext).setIsProfiled(true)
                    Toast.makeText(baseContext, "Profile created", Toast.LENGTH_SHORT).show()
                    dismissProgressBar()
                    next()
                }else{
                    dismissProgressBar()
                    showSnackBar(ErrorUtils.errorBody(response.errorBody()!!), false)
                }

            }

            override fun onFailure(call: Call<UserProfile>, t: Throwable) {
                Toast.makeText(baseContext, "Filed to create", Toast.LENGTH_SHORT).show()
            }

        })

    }

    fun next(){
        intent = Intent(this, HomeActivity::class.java)
        intent?.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }


    override fun onStart() {
        super.onStart()

    }
}