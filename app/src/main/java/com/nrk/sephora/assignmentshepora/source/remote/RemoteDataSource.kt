package com.nrk.sephora.assignmentshepora.source.remote

import com.nrk.sephora.assignmentshepora.models.ProductModel
import retrofit2.HttpException
import java.io.IOException

interface RemoteDataSource {
    suspend fun getAllProductList(nextPage:Int) : ApiReponse<List<ProductModel>>

    suspend fun getProductDetails(productId: String) : ProductModel
}

class DefaultRemoteDataSourceImpl(private val apiService: ProductListingRemoteService) :RemoteDataSource{
    override suspend fun getAllProductList(nextPage:Int):ApiReponse<List<ProductModel>> {
        return try{
            val listOfProducts =  apiService.allProductListing(
                pageNumber = nextPage,
                pageSize = 30,
                include = "featured_variant,featured_ad",
                sort = "sales"
            ).data
            return ApiReponse(listOfProducts,null)
        } catch (e: HttpException){
            ApiReponse(null,ErrorRecord.ServerError)
        }catch (e: IOException){
            ApiReponse(null,ErrorRecord.NetworkError)
        }catch (e: Exception){
            ApiReponse(null,ErrorRecord.GenericError)
        }

    }

    override suspend fun getProductDetails(productId: String):ProductModel {
        TODO("Not yet implemented")
    }
}