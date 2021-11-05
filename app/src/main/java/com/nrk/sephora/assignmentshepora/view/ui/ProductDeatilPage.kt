package com.nrk.sephora.assignmentshepora.view.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.nrk.sephora.assignmentshepora.R
import com.nrk.sephora.assignmentshepora.models.DummyDataHelper
import com.nrk.sephora.assignmentshepora.models.ProductModel
import com.nrk.sephora.assignmentshepora.view.ProductListingViewModel

@Preview(showBackground = true)
@Composable
fun PreviewProductDetailPage() {
    val dummyProduct = DummyDataHelper.createDummyProduct("item1", "this is item1")
    ProductView(dummyProduct)
}

@ExperimentalFoundationApi
@Composable
fun ProductDetailPage(
    productId: String,
    navController:NavHostController,
    viewModel: ProductListingViewModel = hiltViewModel()
) {
    val product: ProductModel =
        viewModel.getLastSelectedItem(productId)
            ?: throw Throwable("No product selected. $productId")

    ProductViewRoot(product){
        navController.navigateUp()
    }
}

@Composable
fun ProductViewRoot(product: ProductModel, onAppBarBack:()->Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = product.attributes.name,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier =
                        Modifier
                            .absolutePadding(right = 18.dp)
                    )

                },
                navigationIcon = {
                    IconButton(onClick = onAppBarBack) {
                        Icon(Icons.Filled.ArrowBack, "")
                    }
                },
                elevation = 12.dp
            )
        },
        content = {
            ProductView(product)
        }
    )
}

@Composable
fun ProductView(product: ProductModel) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
    ) {
        val selectedImage  = rememberSaveable { mutableStateOf(product.attributes.featured_image_urls.firstOrNull()?:"") }
        val selectedImageIndex  = rememberSaveable { mutableStateOf(0) }
        val (headerImage, imageRow, title,subTitle,ratingRow,priceRow,descriptionArea,howToArea,whatItDoes) = createRefs()
        Image(
            contentScale = ContentScale.Crop,
            painter =
            rememberImagePainter(
                data = selectedImage.value,
                builder = {
                    placeholder(R.drawable.ic_launcher_background)
                    crossfade(true)
                }
            ),
            contentDescription = "${product.attributes.name} Image",
            modifier =
            Modifier
                .constrainAs(headerImage) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .aspectRatio(4f / 5f)
                .fillMaxWidth()
        )

        LazyRow(content = {
            itemsIndexed(product.attributes.featured_image_urls) { index, imageUrl ->
                Image(
                    contentScale = ContentScale.Crop,
                    painter =
                    rememberImagePainter(
                        data = imageUrl,
                        builder = {
                            placeholder(R.drawable.ic_launcher_background)
                            crossfade(true)
                        }
                    ),
                    contentDescription = "${product.attributes.name} Image",
                    modifier =
                    Modifier
                        .absolutePadding(top = 8.dp, left = 8.dp, right = 8.dp)
                        .height(50.dp)
                        .width(50.dp)
                        .border(
                            width = 2.dp,
                            color = if(index == selectedImageIndex.value) Color(0xFF3093DF) else Color(
                                0xFF656666
                            ),
                            shape = RectangleShape)
                        .clickable {
                            selectedImageIndex.value = index
                            selectedImage.value = imageUrl
                        }
                )
            }
        },
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .constrainAs(imageRow) {
                    top.linkTo(headerImage.bottom)
                    start.linkTo(headerImage.start)
                    end.linkTo(headerImage.end)
                }
                .fillMaxWidth()
                .wrapContentHeight()
                .absolutePadding(top = 8.dp)
        )
        Text(
            text = product.attributes.name,
            color = Color.Black,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier =
            Modifier
                .constrainAs(title) {
                    top.linkTo(imageRow.bottom)
                    start.linkTo(headerImage.start)
                    end.linkTo(headerImage.end)
                }
                .fillMaxWidth()
                .absolutePadding(8.dp, 8.dp, 8.dp, 0.dp)
        )

        Text(
            text = "%d Variant(s) - %s".format(product.attributes.variants_count , product.attributes.heading),
            color = Color.DarkGray,
            modifier = Modifier
                .constrainAs(subTitle) {
                    top.linkTo(title.bottom)
                    start.linkTo(headerImage.start)
                    end.linkTo(headerImage.end)
                }
                .absolutePadding(top = 4.dp, bottom = 8.dp, left = 12.dp, right = 12.dp),
            fontSize = 14.sp
        )

        Row(
            modifier = Modifier
                .constrainAs(ratingRow) {
                    top.linkTo(subTitle.bottom)
                    start.linkTo(headerImage.start)
                    end.linkTo(headerImage.end)
                }
                .wrapContentHeight()
                .absolutePadding(bottom = 8.dp, left = 12.dp, right = 12.dp)
        ) {
            RatingBar(
                rating = product.attributes.rating.toFloat(),
                modifier =
                Modifier
                    .height(16.dp)
                    .absolutePadding(top = 6.dp),
                color = Color(0xFFC71E9D)

            )
            Text(
                text = "%,d Reviews".format(product.attributes.reviews_count),
                color = Color.Black,
                textDecoration = TextDecoration.Underline,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.absolutePadding(left = 8.dp),
                fontSize = 14.sp
            )
        }

        Row(
            modifier = Modifier
                .constrainAs(priceRow) {
                    top.linkTo(ratingRow.bottom)
                    start.linkTo(headerImage.start)
                    end.linkTo(headerImage.end)
                }
                .absolutePadding(bottom = 8.dp, left = 12.dp, right = 12.dp)
        ) {
            if(product.attributes.original_price  != product.attributes.price){
                Text(
                    text = "%,d".format(product.attributes.original_price),
                    color = Color.Black,
                    maxLines = 1,
                    modifier = Modifier.absolutePadding(right = 8.dp),
                    fontSize = 16.sp,
                    style = TextStyle(textDecoration = TextDecoration.LineThrough)
                )
            }
            Text(
                text = "%,d".format(product.attributes.price),
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.absolutePadding(top = 0.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }

        ComposeParagraphWithTitle(
            title = "DESCRIPTION",
            body = product.attributes.description,
            modifier = Modifier
                .constrainAs(descriptionArea) {
                    top.linkTo(priceRow.bottom)
                    start.linkTo(parent.start)
                    start.linkTo(parent.start)
                }
                .fillMaxWidth()
                .wrapContentHeight()
                .absolutePadding(bottom = 8.dp, left = 6.dp, right = 6.dp)
        )
        ComposeParagraphWithTitle(
            title = "How to use:",
            body = product.attributes.description,
            modifier = Modifier
                .constrainAs(howToArea) {
                    top.linkTo(descriptionArea.bottom)
                    start.linkTo(parent.start)
                    start.linkTo(parent.start)
                }
                .fillMaxWidth()
                .wrapContentHeight()
                .absolutePadding(bottom = 8.dp, left = 6.dp, right = 6.dp)
        )
        ComposeParagraphWithTitle(
            title = "What It Does:",
            body = product.attributes.how_to_text,
            modifier = Modifier
                .constrainAs(whatItDoes) {
                    top.linkTo(howToArea.bottom)
                    start.linkTo(parent.start)
                    start.linkTo(parent.start)
                }
                .fillMaxWidth()
                .wrapContentHeight()
                .absolutePadding(bottom = 8.dp, left = 6.dp, right = 6.dp)
        )
    }

}


@Composable
fun ComposeParagraphWithTitle(title:String, body:String, modifier:Modifier){
    Column(modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start) {
        if(body.isEmpty().not()){
            Text(
                text = title,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp ,
                textAlign = TextAlign.Left,
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight()

            )
            Text(
                text = body,
                color = Color.Black,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp ,
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            )
        }else{
            Text("")
        }

    }

}