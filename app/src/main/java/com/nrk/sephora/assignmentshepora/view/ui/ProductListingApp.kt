package com.nrk.sephora.assignmentshepora.view.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nrk.sephora.assignmentshepora.view.ui.navigation.Destinations.MainPage
import com.nrk.sephora.assignmentshepora.view.ui.navigation.Destinations.ProductDetailPage

@ExperimentalFoundationApi
@Composable
fun ProductListingApp() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = MainPage.route) {
        composable(MainPage.route) {
            MainPage(navController)
        }
        composable("${ProductDetailPage.route}/{${ProductDetailPage.argProductId}}",
            arguments = listOf(
                navArgument(ProductDetailPage.argProductId) {
                    type = NavType.StringType
                    nullable = false
                }
            )) { backStackEntry ->
            val parentEntry: NavBackStackEntry = remember {
                navController.getBackStackEntry(MainPage.route)
            }
            ProductDetailPage(
                productId = backStackEntry.arguments?.getString(ProductDetailPage.argProductId)
                    ?: "",
                viewModel = hiltViewModel(parentEntry),
                navController = navController
            )
        }
    }

}


