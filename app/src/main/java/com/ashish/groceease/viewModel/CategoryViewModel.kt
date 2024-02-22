package com.ashish.groceease.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashish.groceease.model.Category
import com.ashish.groceease.model.Product
import com.ashish.groceease.repository.CategoryRepository
import com.ashish.groceease.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(private val categoryRepository: CategoryRepository):ViewModel() {

    val category: StateFlow<NetworkResult<List<Category>?>?>
        get() = categoryRepository.category

    init {
        getAllCategory()
    }

    fun getAllCategory(){
        viewModelScope.launch {
            categoryRepository.getAllCategory()
        }
    }

    fun getCategoryById(id:String){
        viewModelScope.launch {
            categoryRepository.getCategoryById(id)
        }
    }
}