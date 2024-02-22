package com.ashish.groceease.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashish.groceease.model.Address
import com.ashish.groceease.repository.AddressRepository
import com.ashish.groceease.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(private val addressRepository: AddressRepository) :ViewModel() {
    val address: StateFlow<NetworkResult<List<Address>?>?>
        get() = addressRepository.address

    fun getAllAddress(userId: String) {
        viewModelScope.launch {
            addressRepository.getAllAddress(userId)
        }
    }

    fun getAddressById(addressId: String) {
        viewModelScope.launch {
            addressRepository.getAddressById(addressId)
        }
    }

    fun addNewAddress(address: Address) {
        viewModelScope.launch {
            addressRepository.addNewAddress(address)
        }
    }

    fun updateAddress(addressId: String, address: Address) {
        viewModelScope.launch {
            addressRepository.updateAddress(addressId, address)
        }
    }

    fun deleteAddress(addressId: String) {
        viewModelScope.launch {
            viewModelScope.launch {
                addressRepository.deleteAddress(addressId)
            }
        }
    }
}