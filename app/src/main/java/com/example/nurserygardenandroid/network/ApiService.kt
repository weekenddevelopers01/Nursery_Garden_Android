package com.example.nurserygardenandroid.network

import com.example.nurserygardenandroid.model.order.Order
import com.example.nurserygardenandroid.model.products.Products
import com.example.nurserygardenandroid.model.user.*
import com.example.nurserygardenandroid.utils.Constants
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    /*
      User Profile related Services Such as login, Register, logout
      Auth Routes
     */

    @POST("/user")
     fun loginUser(@Body user: UserLogin): Call<UserAuth>

    @POST("/user/auth")
     fun createUser(@Body user: UserLogin): Call<UserAuth>

     @POST("/user/logout")
     fun logoutUser(@Header(Constants.HEADER_AUTHORIZATION) authorization:String):Call<UserAuth>

     /*
        User Profile once user registered with services
        Such as create profile, getProfile, updateProfile
        UserProfile Routes
      */

    @JvmSuppressWildcards
    @POST("/user/profile")
    fun createUserProfile(@Header(Constants.HEADER_AUTHORIZATION) authorization:String,
                          @Body map:Map<String,Any>): Call<UserProfile>

    @GET("/user/profile")
    fun getUserProfile(@Header(Constants.HEADER_AUTHORIZATION) authorization:String): Call<UserProfile>

    @JvmSuppressWildcards
    @PATCH("/user/profile")
    fun updateUserProfile(@Header(Constants.HEADER_AUTHORIZATION) authorization: String, @Body map: Map<String, Any>):Call<UserProfile>


    @PATCH("/user/address")
    fun addAddress(@Header(Constants.HEADER_AUTHORIZATION) authorization: String, @Body address:Address):Call<UserProfile>

    @GET("/user/address")
    suspend fun getAddress(@Header(Constants.HEADER_AUTHORIZATION) authorization: String):Response<List<Address>>

    @DELETE("/user/address/{aid}")
    fun deleteAddress(@Header(Constants.HEADER_AUTHORIZATION) authorization: String, @Path(Constants.PATH_AID) aid:String): Call<UserProfile>

    @PATCH("/user/wishlist")
    fun addToWishlist(@Header(Constants.HEADER_AUTHORIZATION) authorization: String, @Body wishListItem: WishListItem):Call<UserProfile>

    @POST("/user/wishlist")
    fun wishlistToCart(@Header(Constants.HEADER_AUTHORIZATION) authorization: String, @Body wishListItem: WishListItem):Call<UserProfile>

    @DELETE("/user/wishlist/{cid}")
    fun removeFromWishlist(@Header(Constants.HEADER_AUTHORIZATION) authorization: String, @Path("cid") cid:String):Call<UserProfile>


    /*
        User cart items
     */

    @PATCH("/user/cart")
    fun addCartItem(@Header (Constants.HEADER_AUTHORIZATION) authorization: String, @Body cartItem: CartItem): Call<UserProfile>

    @POST("/user/cart/{op}/{cid}")
    fun addRemoveQty(@Header(Constants.HEADER_AUTHORIZATION) authorization: String, @Path("op") op:String, @Path("cid") cid:String): Call<Products>

    @DELETE("/user/cart/{cid}")
    fun removeCartItem(@Header(Constants.HEADER_AUTHORIZATION) authorization: String, @Path("cid") cid:String): Call<UserProfile>

    /*
        Product related calls like getAll products, get only one products by its id
     */
    @GET("/products")
     fun getProducts(): Call<ArrayList<Products>>

    @GET("/product/{pid}")
    fun getSelectedProduct(@Path(Constants.PATH_PID) id:String): Call<Products>

    @GET("/products/wishlist")
    suspend fun getWishlist(@Header(Constants.HEADER_AUTHORIZATION) authorization: String): Response<List<Products>>

    @GET("/products/cartlist")
    suspend fun getCartList(@Header(Constants.HEADER_AUTHORIZATION) authorization: String): Response<List<Products>>


    /**
     * Order
     */

    @POST("/user/order")
    fun placeOrder(@Header(Constants.HEADER_AUTHORIZATION) authorization: String, @Body order:Order):Call<Order>


    @GET("/user/order")
    fun getOrderList(@Header(Constants.HEADER_AUTHORIZATION) authorization: String):Call<List<Order>>


    @GET("/user/order/{oid}")
    fun getOrder(@Header(Constants.HEADER_AUTHORIZATION) authorization: String, @Path(Constants.PATH_OID)id:String?):Call<Order>

    @PATCH("/user/order/{oid}")
    fun cancelOrder(@Header(Constants.HEADER_AUTHORIZATION) authorization: String, @Path(Constants.PATH_OID)id:String?, @Body map: Map<String,String>):Call<Order>



}