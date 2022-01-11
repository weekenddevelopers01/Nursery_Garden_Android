package com.example.nurserygardenandroid.model.user

data class Address(
    val _id: String? = null,
    val name: String,
    val address: String,
    val city: String,
    val phone: String,
    val state: String,
    val zipcode: String
)