package com.nrk.sephora.assignmentshepora.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nrk.sephora.assignmentshepora.models.ProductModel
import com.nrk.sephora.assignmentshepora.usercase.ProductListingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ProductListingViewModel
@Inject constructor(private val productListingUseCase: ProductListingUseCase) :ViewModel() {


    val productDataSource: Flow<PagingData<ProductModel>> by lazy {
        getAllProducts()
    }


    private fun getAllProducts():Flow<PagingData<ProductModel>> {
        return Pager(PagingConfig(30)) { productListingUseCase.getProductDataSource() }
            .flow
            .cachedIn(viewModelScope)
    }


    // a network request to load more data in progress
    var isNewDataBeingDataLoaded = false

    private lateinit var lastSelectedProductModel:ProductModel

    fun onProductSelect(productModel: ProductModel) {
        this.lastSelectedProductModel = productModel
    }

    fun getLastSelectedItem(productId:String):ProductModel?{
        return if(::lastSelectedProductModel.isInitialized && lastSelectedProductModel.id == productId){
            lastSelectedProductModel
        } else null
    }
}