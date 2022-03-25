package com.example.nurserygardenandroid.model.order

data class Order(
    val __v: Int?,
    val _id: String?,
    val billingAddress: BillingAddress?,
    val date: String?,
    val grandTotal: Double?,
    val invoiceNo: String?,
    val orderItems: ArrayList<OrderItem>,
    val ownerID: String?,
    val status: String?,
    val isDelivered: Boolean,
    val isCancelled: Boolean,
    val totalQty: Int?,
    val addressID: String
)