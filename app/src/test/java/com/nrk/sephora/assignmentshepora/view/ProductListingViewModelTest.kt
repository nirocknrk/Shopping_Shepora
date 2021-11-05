package com.nrk.sephora.assignmentshepora.view

import androidx.paging.PagingSource
import com.google.common.truth.Truth.assertThat
import com.nrk.sephora.assignmentshepora.MainCoroutineRule
import com.nrk.sephora.assignmentshepora.models.Attributes
import com.nrk.sephora.assignmentshepora.models.DummyDataHelper
import com.nrk.sephora.assignmentshepora.models.ProductModel
import com.nrk.sephora.assignmentshepora.source.FakeProductRepository
import com.nrk.sephora.assignmentshepora.source.remote.ErrorRecord
import com.nrk.sephora.assignmentshepora.source.remote.FakeRemoteDataSource
import com.nrk.sephora.assignmentshepora.usecase.FakeProductListingUserCase
import org.junit.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProductListingViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    private lateinit var viewModel: ProductListingViewModel

    private lateinit var fakeUseCase: FakeProductListingUserCase
    private lateinit var fakeRepository: FakeProductRepository
    private lateinit var fakeRemoteDataSource: FakeRemoteDataSource

    @Before
    fun setup() {
        fakeRemoteDataSource = FakeRemoteDataSource()
        fakeRepository = FakeProductRepository(fakeRemoteDataSource)
        fakeUseCase = FakeProductListingUserCase(fakeRepository)
        viewModel = ProductListingViewModel(fakeUseCase)
    }

    @Test
    @Suppress("IllegalIdentifier")
    fun `request selected product by id`() {
        val product = DummyDataHelper.createDummyProduct("Test Item","TEST")
        viewModel.onProductSelect(product)
        val selectedProduct = viewModel.getLastSelectedItem(product.id)
        assertThat(selectedProduct!=null && selectedProduct.id == product.id).isTrue()
    }

    @Test
    @Suppress("IllegalIdentifier")
    fun `request for a product while no product selected`() {
        val product = viewModel.getLastSelectedItem("1")
        assertThat(product == null).isTrue()
    }


    @Test
    @Suppress("IllegalIdentifier")
    fun `initial Loading success`() {
        runBlocking {

            val expectedResult = PagingSource.LoadResult.Page(dummyProductData(), -1, 1)

            fakeRemoteDataSource.setProductsToReturn(dummyProductData())
            fakeUseCase.setFailLoadingPage(-1)

            val actualResult = fakeUseCase.getProductDataSource().load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )

            assertEquals(expectedResult, actualResult)
        }

    }

    @Test
    @Suppress("IllegalIdentifier")
    fun `test subsequent Loading success`() {
        runBlocking {

            val expectedResult = PagingSource.LoadResult.Page(
                data = dummyProductData(),
                prevKey = null,
                nextKey = 2
            )

            fakeRemoteDataSource.setProductsToReturn(dummyProductData())
            fakeUseCase.setFailLoadingPage(-1)

            val actualResult = fakeUseCase.getProductDataSource().load(
                PagingSource.LoadParams.Append(
                    key = 1,
                    loadSize = 6,
                    placeholdersEnabled = false
                )
            )

            assertEquals(expectedResult, actualResult)
        }

    }


    @Test
    @Suppress("IllegalIdentifier")
    fun `initial Loading On No Network`() {
        runBlocking {
            val error = Exception(ErrorRecord.NetworkError.toString(), Throwable())

            val expectedResult = PagingSource.LoadResult.Error<Int, ProductModel>(error)

            fakeRemoteDataSource.setProductsToReturn(dummyProductData())
            fakeUseCase.setFailLoadingPage(0, ErrorRecord.NetworkError)

            val actualResult = fakeUseCase.getProductDataSource().load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )

            assertEquals(expectedResult.toString(), actualResult.toString())
        }

    }

    @Test
    @Suppress("IllegalIdentifier")
    fun `test subsequent Loading On No Network`() {
        runBlocking {
            val error = Exception(ErrorRecord.NetworkError.toString(), Throwable())
            val expectedResult = PagingSource.LoadResult.Error<Int, ProductModel>(error)

            fakeRemoteDataSource.setProductsToReturn(dummyProductData())
            fakeUseCase.setFailLoadingPage(1)

            val actualResult = fakeUseCase.getProductDataSource().load(
                PagingSource.LoadParams.Append(
                    key = 1,
                    loadSize = 9,
                    placeholdersEnabled = false
                )
            )

            assertEquals(expectedResult.toString(), actualResult.toString())
        }

    }

    private fun dummyProductData(): List<ProductModel> {
        return listOf(
            DummyDataHelper.createDummyProduct("item1", "this is item1"),
            DummyDataHelper.createDummyProduct("item2", "this is item2"),
            DummyDataHelper.createDummyProduct("item3", "this is item3")
        )
    }
}
