package com.nrk.sephora.assignmentshepora.di

import com.nrk.sephora.assignmentshepora.source.remote.ProductListingRemoteService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class WebServiceModule {

    @Provides
    fun provideProductListingRemoteService(
        // Potential dependencies of this type
    ): ProductListingRemoteService {
        return Retrofit.Builder()
            .baseUrl("https://api.sephora.sg")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductListingRemoteService::class.java)
    }

}