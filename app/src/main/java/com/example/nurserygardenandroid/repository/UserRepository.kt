package com.example.nurserygardenandroid.repository

import android.location.Address
import com.example.nurserygardenandroid.network.NetworkLayer

class UserRepository {

    suspend fun getAddress(authString: String): List<com.example.nurserygardenandroid.model.user.Address>? {
        val request = NetworkLayer.apiClient.getAddress(authString)

        if(request.isSuccessful){
            return request.body()
        }

        return null
    }
}