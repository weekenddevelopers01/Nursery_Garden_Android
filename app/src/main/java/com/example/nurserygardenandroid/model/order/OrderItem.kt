package com.example.nurserygardenandroid.model.order

data class OrderItem(
    val _id: String?,
    val price: Double?,
    val productID: String,
    val productImage: String?,
    val productName: String?,
    val qty: Int,
    val subTotal: Double?
)