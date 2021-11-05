package com.nrk.sephora.assignmentshepora.usecase

import android.content.Context
import androidx.paging.PagingSource
import com.nrk.sephora.assignmentshepora.models.ProductModel
import com.nrk.sephora.assignmentshepora.usercase.ProductListingUseCase


class FakeProductListingUseCaseTest(val context : Context): ProductListingUseCase {
    override fun getProductDataSource(): PagingSource<Int, ProductModel> {
        TODO("Not yet implemented")
    }

}