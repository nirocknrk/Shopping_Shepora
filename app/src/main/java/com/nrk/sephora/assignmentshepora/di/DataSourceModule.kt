package com.nrk.sephora.assignmentshepora.di

import android.content.SharedPreferences
import com.nrk.sephora.assignmentshepora.source.DefaultProductRepositoryImpl
import com.nrk.sephora.assignmentshepora.source.ProductRepository
import com.nrk.sephora.assignmentshepora.source.local.DefaultLocalDataSourceImpl
import com.nrk.sephora.assignmentshepora.source.local.LocalDataSource
import com.nrk.sephora.assignmentshepora.source.remote.DefaultRemoteDataSourceImpl
import com.nrk.sephora.assignmentshepora.source.remote.ProductListingRemoteService
import com.nrk.sephora.assignmentshepora.source.remote.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
class DataSourceModule {

    @Provides
    fun getRemoteDataSource(productListingRemoteService: ProductListingRemoteService)
    : RemoteDataSource = DefaultRemoteDataSourceImpl(productListingRemoteService)

    @Provides
    fun getLocalDataSource(sharedPreferences: SharedPreferences)
    : LocalDataSource = DefaultLocalDataSourceImpl(sharedPreferences)
}