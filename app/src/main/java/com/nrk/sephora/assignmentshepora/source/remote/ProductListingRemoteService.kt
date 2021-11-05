package com.nrk.sephora.assignmentshepora.source.remote

import com.nrk.sephora.assignmentshepora.models.BaseProductResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ProductListingRemoteService {

    @Headers("Accept: application/json")
    @GET("api/v2.5/products")
    suspend fun allProductListing(
        @Query("page[number]") pageNumber:Int,
        @Query("page[size]") pageSize:Int,
        @Query("include") include:String,
        @Query("sort") sort:String,
    ) : BaseProductResponse

}