package com.example.nurserygardenandroid.model.user

data class UserProfile(
    val __v: Int,
    val _id: String,
    val addressList: List<Address>,
    val authID: String,
    val cartItems: List<CartItem>,
    val createdAt: String,
    val email: String,
    val f_name: String,
    val isVerified: Boolean,
    val l_name: String,
    val phone: String,
    val updatedAt: String,
    val wishListItems: List<WishListItem>
)