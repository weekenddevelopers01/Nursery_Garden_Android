package com.example.nurserygardenandroid.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.nurserygardenandroid.R
import com.example.nurserygardenandroid.databinding.ActivityHome2Binding
import com.example.nurserygardenandroid.model.user.UserProfile
import com.example.nurserygardenandroid.network.NetworkLayer
import com.example.nurserygardenandroid.sharedpreference.SharedPref
import kotlinx.android.synthetic.main.activity_complete_profile.*
import kotlinx.android.synthetic.main.activity_home2.*
import kotlinx.android.synthetic.main.activity_home2.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHome2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var toolbar: Toolbar? = findViewById(R.id.seach_toolbar)

        binding = ActivityHome2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_home2)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications,
                R.id.navigation_cart
            )
        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        NetworkLayer.apiClient.getUserProfile(SharedPref(this).getAuthToken()!!).enqueue(object:Callback<UserProfile>{
            override fun onResponse(call: Call<UserProfile>, response: Response<UserProfile>) {
                if(response.isSuccessful){
                    var userProfile = response.body()
                    SharedPref(this@HomeActivity)
                        .setUserDeails(userProfile?.f_name+" "+userProfile?.l_name, userProfile!!.phone, userProfile?.email )
                    Toast.makeText(baseContext, "User Profile saved", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(baseContext, "Error In fetching User details", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserProfile>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

    }

    fun cartOnclick(item: android.view.MenuItem) {
        if(item.itemId == R.id.cart){
            var intent = Intent(this, ProductDetailActivity::class.java)
            startActivity(intent)
        }
    }




}