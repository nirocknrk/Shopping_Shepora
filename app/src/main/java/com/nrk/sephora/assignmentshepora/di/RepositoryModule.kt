package com.nrk.sephora.assignmentshepora.di

import com.nrk.sephora.assignmentshepora.source.DefaultProductRepositoryImpl
import com.nrk.sephora.assignmentshepora.source.ProductRepository
import com.nrk.sephora.assignmentshepora.source.local.LocalDataSource
import com.nrk.sephora.assignmentshepora.source.remote.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
class RepositoryModule {

    @Provides
    fun getProductListSource(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource): ProductRepository = DefaultProductRepositoryImpl(localDataSource,remoteDataSource)
}