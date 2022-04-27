package com.example.nurserygardenandroid.network

import com.example.nurserygardenandroid.model.order.Order
import com.example.nurserygardenandroid.model.products.Products
import com.example.nurserygardenandroid.model.user.*
import com.example.nurserygardenandroid.network.ApiService
import com.example.nurserygardenandroid.utils.Constants
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Header

class ApiClient(private  val apiService: ApiService) {

    /*
      User Profile related Services Such as login, Register, logout
      Auth Routes
     */

     fun loginUser(user: UserLogin): Call<UserAuth>{
        return apiService.loginUser(user)
    }

     fun createUser(user: UserLogin): Call<UserAuth>{
        return apiService.createUser(user)
    }

     fun logoutUser(authorization:String): Call<UserAuth> {
        return apiService.logoutUser(authorization)
    }


    /*
        User Profile once user registered with services
        Such as create profile, getProfile, updateProfile
        UserProfile Routes
      */
    @JvmSuppressWildcards
    fun createUserProfile(map:Map<String,Any>, authorization:String): Call<UserProfile>{
        return apiService.createUserProfile(authorization, map)
    }

    fun getUserProfile(authorization:String): Call<UserProfile>{
        return apiService.getUserProfile(authorization)
    }

    @JvmSuppressWildcards
    fun updateUserProfile(authorization: String, map: Map<String, Any>): Call<UserProfile>{
        return  apiService.updateUserProfile(authorization, map)
    }

    fun addAddress(authorization: String, address: Address):Call<UserProfile>{
        return apiService.addAddress(authorization, address)
    }

    suspend fun getAddress(authorization: String):Response<List<Address>>{
        return  apiService.getAddress(authorization)
    }

    fun deleteAddress(authorization: String, aid:String): Call<UserProfile>{
        return apiService.deleteAddress(authorization, aid)
    }

    fun addToWishlist(authorization: String, wishListItem: WishListItem):Call<UserProfile>{
        return apiService.addToWishlist(authorization, wishListItem)
    }

    fun wishlistToCart(authorization: String, wishListItem: WishListItem):Call<UserProfile>{
        return apiService.wishlistToCart(authorization, wishListItem)
    }

    fun removeFromWishlist(authorization: String, cid: String):Call<UserProfile>{
        return apiService.removeFromWishlist(authorization, cid)
    }

    fun addCartItem(authorization: String,cartItem: CartItem):Call<UserProfile> {
        return apiService.addCartItem(authorization, cartItem)
    }

    fun addRemoveQty(authorization: String, op:String, cid:String): Call<Products> {
        return apiService.addRemoveQty(authorization, op, cid)
    }

    fun removeCartItem(authorization: String, cid: String): Call<UserProfile>{
        return apiService.removeCartItem(authorization, cid)
    }


    /*
        Product Routes
     */

//     fun getProducts(): Response<List<Products>>{
//        return  apiService.getProducts()
//    }
    fun getProducts(): Call<ArrayList<Products>>{
        return  apiService.getProducts()
    }
    fun getSelectedProduct(pid:String):Call<Products>{
        return  apiService.getSelectedProduct(pid)
    }

    suspend fun getWishlist(authorization: String): Response<List<Products>>{
        return apiService.getWishlist(authorization)
    }

    suspend fun getCartList(authorization: String): Response<List<Products>>{
        return apiService.getCartList(authorization)
    }


    /**
     * order
     */

    fun placeOrder(authorization: String, order:Order):Call<Order>{
        return apiService.placeOrder(authorization, order)
    }
    fun getOrderList(authorization: String):Call<List<Order>>{
        return apiService.getOrderList(authorization)
    }

    fun getOrder(authorization: String, id: String?):Call<Order>{
        return apiService.getOrder(authorization, id)
    }

    fun cancelOrder(authorization: String, id: String?):Call<Order>{
        var map = HashMap<String,String>()
        map.put("status","Order Canceled")
        return apiService.cancelOrder(authorization, id, map )
    }



}