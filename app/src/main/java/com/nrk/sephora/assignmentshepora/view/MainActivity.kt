package com.nrk.sephora.assignmentshepora.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.nrk.sephora.assignmentshepora.models.Attributes
import com.nrk.sephora.assignmentshepora.models.ProductModel
import com.nrk.sephora.assignmentshepora.ui.theme.AssignmentSheporaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: ProductListingViewModel by viewModels()

    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getAllProducts()

        viewModel.allProduct.observe(this){

            setContent {
                AssignmentSheporaTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(color = MaterialTheme.colors.background) {
                        MainPage( it)
                    }
                }
            }
        }

    }
}

@Composable
fun Greeting(name: String,products: List<ProductModel>) {
    Text(text = "Hello $name!")
    MessageList(products)
}
val sampleData = listOf(
    ProductModel(Attributes("item1","this is item1"),"id","type"),
    ProductModel(Attributes("item2","this is item2"),"id","type"),
    ProductModel(Attributes("item3","this is item3"),"id","type")
)

@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AssignmentSheporaTheme {
        MainPage(sampleData)

    }
}

@ExperimentalFoundationApi
@Composable
fun MainPage(products: List<ProductModel>){
    Scaffold(topBar = {
        HomeAppBar(
            title = "Epic World",{ }
        )
    },
        content = { productListing(products) }
    )
}

@Composable
fun MessageList(products: List<ProductModel>) {
    Column {
        products.forEach { product ->
            Text(product.attributes.name)
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun ProductListing(
    products: List<ProductModel>
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        content = {
            items(products.size) { index ->
                products[index].let {
                    Box(Modifier.fillMaxWidth()) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = it.attributes.name,
                            color = Color.Black
                        )
                    }

                }
            }
        }
    )
}

@Composable
fun HomeAppBar(title: String, openSearch: () -> Unit) {
    TopAppBar(
        title = { Text(text = title) },
        actions = {
            IconButton(onClick = openSearch) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
            }
        }
    )
}
