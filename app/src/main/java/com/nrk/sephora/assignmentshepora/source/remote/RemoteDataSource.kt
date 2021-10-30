package com.nrk.sephora.assignmentshepora.source.remote

import android.util.Log
import com.nrk.sephora.assignmentshepora.models.ProductModel
import retrofit2.HttpException
import javax.inject.Inject

interface RemoteDataSource {
    suspend fun getAllProductList() : List<ProductModel>

    suspend fun getProductDetails(productId: String) : ProductModel
}

class DefaultRemoteDataSourceImpl(private val apiService: ProductListingRemoteService) :RemoteDataSource{
    override suspend fun getAllProductList():List<ProductModel> {
        try{
            val listOfProducts =  apiService.allProductListing().data
            Log.i("Prod","API returned ${listOfProducts.size}")
            return listOfProducts
        } catch (e: HttpException){
            Log.e("Prod","API returned http error",e)
        }catch (e: Exception){
            Log.e("Prod","API returned  error",e)
        }
        return emptyList()
    }

    override suspend fun getProductDetails(productId: String):ProductModel {
        TODO("Not yet implemented")
    }
}