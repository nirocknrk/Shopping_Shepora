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



}
