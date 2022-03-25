package com.example.nurserygardenandroid.model.products

import java.nio.Buffer

data class Products(
    val __v: Int,
    val _id: String,
    val description: String,
    val productCode: String,
    val productName: String,
    val productPrice: Double,
    var qty: Int,
    val status: Boolean,
    val productImage: String,
    val height: Double,
    val weight: Double,
    val life: String,
    val rating: Double

)

data class image(val type:Any, val data:Array<Buffer>)