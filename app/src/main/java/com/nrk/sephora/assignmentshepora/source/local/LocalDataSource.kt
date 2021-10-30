package com.nrk.sephora.assignmentshepora.source.local

import android.content.SharedPreferences
import com.nrk.sephora.assignmentshepora.models.ProductModel

interface LocalDataSource{
    suspend fun getAllProductList() : List<ProductModel>

    suspend fun getProductDetails(productId: String) :ProductModel
}

class DefaultLocalDataSourceImpl (private val sharedPreferences:SharedPreferences) :LocalDataSource{
    override suspend fun getAllProductList():List<ProductModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getProductDetails(productId: String):ProductModel {
        TODO("Not yet implemented")
    }
}