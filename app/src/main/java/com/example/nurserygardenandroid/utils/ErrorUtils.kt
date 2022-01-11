package com.example.nurserygardenandroid.utils

import com.example.nurserygardenandroid.model.ApiError
import com.google.gson.GsonBuilder
import okhttp3.ResponseBody

open class ErrorUtils {

    companion object {
        fun errorBody(errorBody: ResponseBody):String{
           var gson = GsonBuilder().create()
           var pojo = gson.fromJson(errorBody.string(), ApiError::class.java)
           return pojo.message
       }
    }
}