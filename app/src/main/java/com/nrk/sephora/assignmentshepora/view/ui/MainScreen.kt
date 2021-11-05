package com.nrk.sephora.assignmentshepora.view.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.nrk.sephora.assignmentshepora.R
import com.nrk.sephora.assignmentshepora.models.ProductModel
import com.nrk.sephora.assignmentshepora.view.ProductListingViewModel
import com.nrk.sephora.assignmentshepora.view.ui.navigation.Destinations.ProductDetailPage

@ExperimentalFoundationApi
@Composable
fun MainPage(
    navHostController: NavHostController,
    viewModel: ProductListingViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_title),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                elevation = 12.dp
            )
        },
        content = {
            ProductListingFromLiveData(viewModel, scaffoldState) { product ->
                viewModel.onProductSelect(product)
                navHostController.navigate(ProductDetailPage.withArgs(product.id))
            }
        },
        scaffoldState = scaffoldState,
        snackbarHost = { scaffoldState.snackbarHostState }
    )
}

@ExperimentalFoundationApi
@Composable
fun ProductListingFromLiveData(
    viewModel: ProductListingViewModel,
    scaffoldState: ScaffoldState,
    onProductSelect: (ProductModel) -> Unit
) {

    val lazyProductItems = viewModel.productDataSource.collectAsLazyPagingItems()
    val (snackBarMessageRes, setSnackBarMessage) = rememberSaveable { mutableStateOf<Int?>(null) }

    lazyProductItems.let { productItems ->
        LazyVerticalGrid(cells = GridCells.Fixed(2), content = {
            items(productItems.itemCount) { index ->
                productItems[index]?.let {
                    ProductItem(product = it, onProductSelect = onProductSelect)
                }
            }

            productItems.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        viewModel.isNewDataBeingDataLoaded = true
                        setSnackBarMessage(null)
                        item { LoadingItem() }
                        item { LoadingItem() }
                    }
                    loadState.append is LoadState.Loading || loadState.prepend is LoadState.Loading -> {
                        viewModel.isNewDataBeingDataLoaded = true
                        setSnackBarMessage(null)
                        item { LoadingItem() }
                        item { LoadingItem() }
                    }
                    loadState.refresh is LoadState.Error -> {
                        if (viewModel.isNewDataBeingDataLoaded) {
                            viewModel.isNewDataBeingDataLoaded = false
                            setSnackBarMessage(R.string.network_error_on_start)
                        }

                    }
                    loadState.append is LoadState.Error || loadState.prepend is LoadState.Error -> {
                        if (viewModel.isNewDataBeingDataLoaded) {
                            viewModel.isNewDataBeingDataLoaded = false
                            setSnackBarMessage(R.string.network_error_on_load_more)
                        }
                    }
                }
            }
        })
    }

    DefaultSnackBar(snackBarMessageRes = snackBarMessageRes, scaffoldState = scaffoldState) {
        setSnackBarMessage(null)
    }


}

@Composable
fun LoadingItem() {
    CircularProgressIndicator(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .wrapContentWidth(
                Alignment.CenterHorizontally
            )
    )
}

@Composable
fun DefaultSnackBar(
    snackBarMessageRes: Int?,
    scaffoldState: ScaffoldState,
    actionLabel: String = stringResource(id = R.string.snackbar_action_ok),
    actionPerformed: () -> Unit
) {
    SnackbarHost(
        hostState = scaffoldState.snackbarHostState,
        snackbar = { snackBarData ->
            Snackbar(snackbarData = snackBarData, Modifier.padding(16.dp))
        })

    if (snackBarMessageRes != null) {
        val messageString = stringResource(id = snackBarMessageRes)
        LaunchedEffect(key1 = 1) {
            val snackBarResult = scaffoldState.snackbarHostState.showSnackbar(
                message = messageString,
                actionLabel = actionLabel,
                duration = SnackbarDuration.Indefinite
            )
            when (snackBarResult) {
                SnackbarResult.Dismissed -> {
                }
                SnackbarResult.ActionPerformed -> {
                    actionPerformed.invoke()
                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                }
            }
        }

    } else {
        scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
    }

}
