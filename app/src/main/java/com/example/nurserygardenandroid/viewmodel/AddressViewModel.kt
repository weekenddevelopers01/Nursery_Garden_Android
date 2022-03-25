package com.example.nurserygardenandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nurserygardenandroid.model.user.Address
import com.example.nurserygardenandroid.repository.UserRepository
import kotlinx.coroutines.launch

class AddressViewModel: ViewModel() {

    private val respository = UserRepository()

    private val _getAddress = MutableLiveData<List<Address>?>()
    val getAddress: LiveData<List<Address>?> = _getAddress

    fun refresh(authString: String){
        viewModelScope.launch {
            val response = respository.getAddress(authString)
            _getAddress.postValue(response)
        }
    }

}