package com.nrk.sephora.assignmentshepora.usecase

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nrk.sephora.assignmentshepora.models.ProductModel
import com.nrk.sephora.assignmentshepora.source.FakeProductRepository
import com.nrk.sephora.assignmentshepora.source.remote.ApiReponse
import com.nrk.sephora.assignmentshepora.source.remote.ErrorRecord
import com.nrk.sephora.assignmentshepora.usercase.ProductListingUseCase

class FakeProductListingUserCase(val fakeProductRepository: FakeProductRepository): ProductListingUseCase{

    private var failAtPage = -1
    private val pageLoadingError:ErrorRecord = ErrorRecord.NetworkError

    fun setFailLoadingPage(pageNumberToFail:Int, failFor:ErrorRecord = ErrorRecord.NetworkError){
        this.failAtPage = pageNumberToFail
    }

    override fun getProductDataSource(): PagingSource<Int, ProductModel> {
        return object : PagingSource<Int, ProductModel>() {
            override fun getRefreshKey(state: PagingState<Int, ProductModel>): Int? {
                return state.anchorPosition?.let { anchorPosition ->
                    state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                        ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
                }
            }

            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductModel> {
                val nextPage = params.key ?: 1
                val productListingResponse = if(failAtPage != nextPage){
                    fakeProductRepository.getAllProducts(nextPage)
                }else{
                    ApiReponse(null,pageLoadingError)
                }

                return if (productListingResponse.data == null) {
                    LoadResult.Error(Exception(productListingResponse.error.toString()))
                } else {
                    LoadResult.Page(
                        data = productListingResponse.data!!,
                        prevKey = if (nextPage == 1) null else nextPage - 1,
                        nextKey = nextPage.plus(1)
                    )
                }
            }
        }
    }

}