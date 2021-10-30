package com.nrk.sephora.assignmentshepora.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nrk.sephora.assignmentshepora.models.ProductModel
import com.nrk.sephora.assignmentshepora.usercase.ProductListingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProductListingViewModel @Inject constructor(val productListingUseCase: ProductListingUseCase) :ViewModel() {

    private val _allProduct = MutableLiveData<List<ProductModel>>()
    val allProduct: LiveData<List<ProductModel>> = _allProduct

    fun getAllProducts(){
        productListingUseCase.getProductListing()
            .onEach {
                _allProduct.value = it
                Log.d("Prod"," got result ${it.joinToString { it.attributes.name }}")
            }
            .launchIn(viewModelScope)
    }
}