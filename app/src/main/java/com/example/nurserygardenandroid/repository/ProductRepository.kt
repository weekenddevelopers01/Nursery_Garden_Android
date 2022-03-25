package com.example.nurserygardenandroid.repository

import com.example.nurserygardenandroid.model.products.Products
import com.example.nurserygardenandroid.network.NetworkLayer

class ProductRepository {

    suspend fun getProducts(): List<Products>? {
        val request =NetworkLayer.apiClient.getProducts()

        if (request.isSuccessful){
            return  request.body()!!
        }

        return null
    }

    suspend fun getWishlist(authorization:String):List<Products>?{
        val request = NetworkLayer.apiClient.getWishlist(authorization)
        if(request.isSuccessful){
            return request.body()!!
        }

        return null
    }

    suspend fun getCartlist(authorization:String):List<Products>?{
        val request = NetworkLayer.apiClient.getCartList(authorization)
        if(request.isSuccessful){
            return request.body()!!
        }

        return null
    }
}