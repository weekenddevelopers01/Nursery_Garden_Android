package com.example.nurserygardenandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nurserygardenandroid.model.products.Products
import com.example.nurserygardenandroid.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

    private val repository = ProductRepository()

    private val _getProducts = MutableLiveData<List<Products>?>()
    val getProducts: LiveData<List<Products>?> = _getProducts

//    fun refresh(){
//        viewModelScope.launch {
//            val response = repository.getProducts()
//            _getProducts.postValue(response)
//        }
//    }


    fun getWishList(autherization:String){
        viewModelScope.launch {
            val response = repository.getWishlist(autherization)
            _getProducts.postValue(response)
        }
    }

    fun getCartlist(autherization:String){
        viewModelScope.launch {
            val response = repository.getCartlist(autherization)
            _getProducts.postValue(response)
        }
    }
}