package com.nrk.sephora.assignmentshepora.source.remote

import com.nrk.sephora.assignmentshepora.models.ProductModel

class FakeRemoteDataSource:RemoteDataSource {

    private var errorRecord :ErrorRecord? = null

    private var productList = mutableListOf<ProductModel>()

    fun shouldReturnError(errorRecord: ErrorRecord?){
        this.errorRecord = errorRecord
    }

    fun setProductsToReturn(products: List<ProductModel>,clearAndAdd:Boolean = true){
        if(clearAndAdd) productList.clear()
        productList.addAll(products)
    }

    override suspend fun getAllProductList(nextPage: Int): ApiReponse<List<ProductModel>>  =
        if(errorRecord == null ) ApiReponse(productList,null)
        else ApiReponse(null,errorRecord)


    override suspend fun getProductDetails(productId: String): ProductModel =
        productList.find { it.id == productId } ?: throw  Throwable("No such product")
}