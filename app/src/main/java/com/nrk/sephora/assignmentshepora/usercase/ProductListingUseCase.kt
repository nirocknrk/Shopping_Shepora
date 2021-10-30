package com.nrk.sephora.assignmentshepora.usercase

import com.nrk.sephora.assignmentshepora.models.ProductModel
import com.nrk.sephora.assignmentshepora.source.ProductRepository
import kotlinx.coroutines.flow.Flow

interface ProductListingUseCase {
    fun getProductListing():Flow<List<ProductModel>>
}

class DefaultProductListingUseCaseImpl(private val productRepository: ProductRepository) :ProductListingUseCase{
    override fun getProductListing():Flow<List<ProductModel>> =
        productRepository.getAllProducts()

}
