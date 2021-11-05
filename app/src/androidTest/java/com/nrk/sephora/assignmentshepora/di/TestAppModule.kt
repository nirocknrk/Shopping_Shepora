package com.nrk.sephora.assignmentshepora.di

import android.content.Context
import com.nrk.sephora.assignmentshepora.usecase.FakeProductListingUseCaseTest
import com.nrk.sephora.assignmentshepora.usercase.ProductListingUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.components.SingletonComponent
import org.junit.Rule
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Provides
    @Named("fakeProductListingViewModelTest")
    fun provideFakeUseCase(@ApplicationContext context: Context):ProductListingUseCase
    = FakeProductListingUseCaseTest(context = context)
}