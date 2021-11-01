package com.nrk.sephora.assignmentshepora.view.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.nrk.sephora.assignmentshepora.models.ProductModel
import com.nrk.sephora.assignmentshepora.view.HomeAppBar
import com.nrk.sephora.assignmentshepora.view.LoadingItem
import com.nrk.sephora.assignmentshepora.view.ProductListingViewModel
import com.nrk.sephora.assignmentshepora.view.ui.navigation.Destinations.ProductDetailPage

@ExperimentalFoundationApi
@Composable
fun MainPage(
    navHostController: NavHostController,
    viewModel: ProductListingViewModel = hiltViewModel()
){
    Scaffold(
        topBar = { HomeAppBar(title = "Shepora Mobile") { } },
        content = {
            ProductListingFromLiveData(viewModel){ product ->
                viewModel.lastSelectedProduct(product)
                navHostController.navigate(ProductDetailPage.withArgs(product.id))
            }
        }
    )
}

@ExperimentalFoundationApi
@Composable
fun ProductListingFromLiveData(viewModel: ProductListingViewModel,onProductSelect:(ProductModel)->Unit ){

    val lazyProductItems = viewModel.productDataSource.collectAsLazyPagingItems()

    lazyProductItems.let { productItems ->
        LazyVerticalGrid(cells = GridCells.Fixed(2), content = {
            items(productItems.itemCount) { index ->
                productItems[index]?.let {
                    ProductItem(product= it,  onProductSelect= onProductSelect)
                }
            }

            productItems.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item { LoadingItem() }
                        item { LoadingItem() }
                    }
                    loadState.append is LoadState.Loading -> {
                        item { LoadingItem() }
                        item { LoadingItem() }
                    }
                    loadState.refresh is LoadState.Error -> {
                        viewModel.handlePaginationDataError()
                    }
                    loadState.append is LoadState.Error -> {

                        viewModel.handlePaginationAppendError(
                            "Fail to fetch more products. Please try again later.", "Ok")
                    }
                }
            }
        })
    }
}