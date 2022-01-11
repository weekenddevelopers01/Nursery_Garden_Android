package com.example.nurserygardenandroid.network

import com.example.nurserygardenandroid.model.ApiClient
import com.example.nurserygardenandroid.model.Products
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkLayer{

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:3003/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService : ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    val apiClient = ApiClient(apiService)
}