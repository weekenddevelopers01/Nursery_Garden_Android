package com.example.nurserygardenandroid.network

import com.example.nurserygardenandroid.utils.ProdConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkLayer{


    //.baseUrl("http://10.0.2.2:3003/") for localhost
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(ProdConstants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService : ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    val apiClient = ApiClient(apiService)
}