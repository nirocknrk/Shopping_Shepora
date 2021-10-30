package com.nrk.sephora.assignmentshepora.source

import com.nrk.sephora.assignmentshepora.models.ProductModel
import com.nrk.sephora.assignmentshepora.source.local.LocalDataSource
import com.nrk.sephora.assignmentshepora.source.remote.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface ProductRepository {
    fun getAllProducts() : Flow<List<ProductModel>>
    fun getProduct(productId:String) : Flow<ProductModel>
}

class DefaultProductRepositoryImpl(private val localDataSource: LocalDataSource,
                                   private val remoteDataSource: RemoteDataSource):ProductRepository {
    override fun getAllProducts():Flow<List<ProductModel>>  = flow<List<ProductModel>>{
        emit(remoteDataSource.getAllProductList())
    }

    override fun getProduct(productId:String): Flow<ProductModel> = flow{
        emit(remoteDataSource.getProductDetails(productId))
    }
}