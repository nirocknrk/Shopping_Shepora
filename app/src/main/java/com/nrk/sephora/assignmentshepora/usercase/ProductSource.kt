package com.nrk.sephora.assignmentshepora.usercase

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nrk.sephora.assignmentshepora.models.ProductModel
import com.nrk.sephora.assignmentshepora.source.ProductRepository
import javax.inject.Inject

class ProductSource @Inject constructor(private val productRepository: ProductRepository): PagingSource<Int, ProductModel>() {
    override fun getRefreshKey(state: PagingState<Int, ProductModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductModel> {
        val nextPage = params.key ?: 1
        val productListingResponse = productRepository.getAllProducts(nextPage)

        return if (productListingResponse.data == null) {
            LoadResult.Error(Exception(productListingResponse.error.toString()))
        } else {
            LoadResult.Page(
                data = productListingResponse.data,
                prevKey = if (nextPage == 1) null else nextPage-1,
                nextKey = nextPage.plus(1)
            )
        }
    }
}