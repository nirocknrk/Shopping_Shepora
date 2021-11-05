package com.nrk.sephora.assignmentshepora.view


import com.nrk.sephora.assignmentshepora.di.TestAppModule.hiltRule
import com.nrk.sephora.assignmentshepora.usercase.ProductListingUseCase
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import javax.inject.Inject
import javax.inject.Named


@HiltAndroidTest
class FakeProductListingViewModelTest {

    private lateinit var productListingUseCase: ProductListingUseCase

    @Inject
    @Named("fakeProductListingViewModelTest")
    private lateinit var viewModel: ProductListingViewModel

    @Before
    fun setupViewModel() {
         hiltRule.inject()

    }


}