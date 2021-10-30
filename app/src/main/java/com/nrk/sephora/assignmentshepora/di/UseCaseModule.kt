package com.nrk.sephora.assignmentshepora.di

import com.nrk.sephora.assignmentshepora.source.ProductRepository
import com.nrk.sephora.assignmentshepora.usercase.DefaultProductListingUseCaseImpl
import com.nrk.sephora.assignmentshepora.usercase.ProductListingUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
class UseCaseModule {

    @Provides
    fun getProductListingUseCase(productRepository: ProductRepository): ProductListingUseCase = DefaultProductListingUseCaseImpl(productRepository)
}