package com.example.nurserygardenandroid.network

import com.example.nurserygardenandroid.model.Products
import com.example.nurserygardenandroid.model.user.Address
import com.example.nurserygardenandroid.model.user.UserAuth
import com.example.nurserygardenandroid.model.user.UserLogin
import com.example.nurserygardenandroid.model.user.UserProfile
import com.example.nurserygardenandroid.utils.Constants
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    /*
      User Profile related Services Such as login, Register, logout
      Auth Routes
     */

    @POST("/user")
     fun loginUser(@Body user: UserLogin): Call<UserAuth>

    @POST("/user/auth")
     fun createUser(@Body user: UserLogin): Call<UserAuth>

     @POST("/user/logout")
     fun logoutUser(@Header(Constants.HEADER_AUTHORIZATION) authorization:String):Call<UserAuth>

     /*
        User Profile once user registered with services
        Such as create profile, getProfile, updateProfile
        UserProfile Routes
      */

    @JvmSuppressWildcards
    @POST("/user/profile")
    fun createUserProfile(@Header(Constants.HEADER_AUTHORIZATION) authorization:String,
                          @Body map:Map<String,Any>): Call<UserProfile>

    @GET("/user/profile")
    fun getUserProfile(@Header(Constants.HEADER_AUTHORIZATION) authorization:String): Call<UserProfile>

    @JvmSuppressWildcards
    @PATCH("/user/profile")
    fun updateUserProfile(@Header(Constants.HEADER_AUTHORIZATION) authorization: String, @Body map: Map<String, Any>):Call<UserProfile>


    @PATCH("/user/address")
    fun addAddress(@Header(Constants.HEADER_AUTHORIZATION) authorization: String, @Body address:Address):Call<UserProfile>

    @GET("/user/address")
    suspend fun getAddress(@Header(Constants.HEADER_AUTHORIZATION) authorization: String):Response<List<Address>>

    @DELETE("/user/address/{aid}")
    fun deleteAddress(@Header(Constants.HEADER_AUTHORIZATION) authorization: String, @Path(Constants.QUERY_AID) aid:String): Call<UserProfile>


    @GET("/products")
    suspend fun getProducts(): Response<List<Products>>

}