package com.nrk.sephora.assignmentshepora.source.remote

import com.nrk.sephora.assignmentshepora.models.BaseProductResponse
import retrofit2.http.GET

interface ProductListingRemoteService {

    @GET("api/v2.5/products")
    suspend fun allProductListing() : BaseProductResponse
}