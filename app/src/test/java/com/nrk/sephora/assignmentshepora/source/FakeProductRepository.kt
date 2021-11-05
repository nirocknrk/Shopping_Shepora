package com.nrk.sephora.assignmentshepora.source

import com.nrk.sephora.assignmentshepora.models.ProductModel
import com.nrk.sephora.assignmentshepora.source.remote.ApiReponse
import com.nrk.sephora.assignmentshepora.source.remote.FakeRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeProductRepository(val fakeRemoteDataSource: FakeRemoteDataSource) : ProductRepository{


    override suspend fun getAllProducts(nextPage: Int): ApiReponse<List<ProductModel>> =
        fakeRemoteDataSource.getAllProductList(nextPage)


    override fun getProduct(productId: String): Flow<ProductModel>  =
        flow<ProductModel> { fakeRemoteDataSource.getProductDetails(productId) }

}