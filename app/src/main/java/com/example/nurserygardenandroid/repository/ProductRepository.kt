package com.example.nurserygardenandroid.repository

import com.example.nurserygardenandroid.model.Products
import com.example.nurserygardenandroid.network.NetworkLayer

class ProductRepository {

    suspend fun getProducts(): List<Products>? {
        val request =NetworkLayer.apiClient.getProducts()

        if (request.isSuccessful){
            return  request.body()!!
        }

        return null
    }


}