package com.example.nurserygardenandroid.model.order

data class BillingAddress(
    val _id: String,
    val address: String,
    val city: String,
    val phone: String,
    val state: String,
    val zipcode: String
)