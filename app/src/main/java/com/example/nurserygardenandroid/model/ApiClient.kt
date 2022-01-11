package com.example.nurserygardenandroid.model

import com.example.nurserygardenandroid.model.user.Address
import com.example.nurserygardenandroid.model.user.UserAuth
import com.example.nurserygardenandroid.model.user.UserLogin
import com.example.nurserygardenandroid.model.user.UserProfile
import com.example.nurserygardenandroid.network.ApiService
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field

class ApiClient(private  val apiService: ApiService) {

    /*
      User Profile related Services Such as login, Register, logout
      Auth Routes
     */

     fun loginUser(user: UserLogin): Call<UserAuth>{
        return apiService.loginUser(user)
    }

     fun createUser(user: UserLogin): Call<UserAuth>{
        return apiService.createUser(user)
    }

     fun logoutUser(authorization:String): Call<UserAuth> {
        return apiService.logoutUser(authorization)
    }


    /*
        User Profile once user registered with services
        Such as create profile, getProfile, updateProfile
        UserProfile Routes
      */
    @JvmSuppressWildcards
    fun createUserProfile(map:Map<String,Any>, authorization:String): Call<UserProfile>{
        return apiService.createUserProfile(authorization, map)
    }

    fun getUserProfile(authorization:String): Call<UserProfile>{
        return apiService.getUserProfile(authorization)
    }

    @JvmSuppressWildcards
    fun updateUserProfile(authorization: String, map: Map<String, Any>): Call<UserProfile>{
        return  apiService.updateUserProfile(authorization, map)
    }

    fun addAddress(authorization: String, address: Address):Call<UserProfile>{
        return apiService.addAddress(authorization, address)
    }

    suspend fun getAddress(authorization: String):Response<List<Address>>{
        return  apiService.getAddress(authorization)
    }

    fun deleteAddress(authorization: String, aid:String): Call<UserProfile>{
        return apiService.deleteAddress(authorization, aid)
    }


    suspend fun getProducts(): Response<List<Products>>{
        return  apiService.getProducts()
    }


}