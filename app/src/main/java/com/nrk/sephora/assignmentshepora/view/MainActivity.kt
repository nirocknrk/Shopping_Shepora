package com.nrk.sephora.assignmentshepora.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nrk.sephora.assignmentshepora.models.Attributes
import com.nrk.sephora.assignmentshepora.models.ProductModel
import com.nrk.sephora.assignmentshepora.ui.theme.AssignmentSheporaTheme
import com.nrk.sephora.assignmentshepora.view.ui.ProductItem
import com.nrk.sephora.assignmentshepora.view.ui.ProductListingApp
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalFoundationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            AssignmentSheporaTheme {
                Surface(color = MaterialTheme.colors.background) {
                    ProductListingApp()
                }
            }
        }


    }

}


@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AssignmentSheporaTheme {
        Scaffold(
            topBar = { HomeAppBar(title = "Shepora Mobile") { } },
            content = {
                ProductListing(sampleData) {}
            }
        )

    }
}


@ExperimentalFoundationApi
@Composable
fun ProductListing(productsList: List<ProductModel>, onProductSelect: (ProductModel) -> Unit) {

    if (productsList.isEmpty()) {
        LoadingItem()
    }
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        content = {
            items(productsList.size) { index ->
                ProductItem(productsList[index], onProductSelect)
            }
        }
    )
}

@Composable
fun HomeAppBar(title: String, openSearch: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        actions = {
            IconButton(onClick = openSearch) {
                Icon(imageVector = Icons.Filled.Search,
                    contentDescription = "Search")
            }
        }
    )
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

val sampleData = listOf(
    ProductModel(Attributes("item1", "this is item1"), "id", "type"),
    ProductModel(Attributes("item2", "this is item2"), "id", "type"),
    ProductModel(Attributes("item3", "this is item3"), "id", "type")
)
