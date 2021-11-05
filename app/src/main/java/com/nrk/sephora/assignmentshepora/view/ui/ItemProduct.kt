package com.nrk.sephora.assignmentshepora.view.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberImagePainter
import com.nrk.sephora.assignmentshepora.R
import com.nrk.sephora.assignmentshepora.models.DummyDataHelper
import com.nrk.sephora.assignmentshepora.models.ProductModel
import com.nrk.sephora.assignmentshepora.ui.theme.AssignmentSheporaTheme

val dummyItem = DummyDataHelper.createDummyProduct("item3\ndads","this is item33\n" +"dads3\n" + "dads")
@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun DefaultItemProductPreview() {
    AssignmentSheporaTheme {
        ProductItem(dummyItem){}

    }
}

@Composable
fun ProductItem(product: ProductModel,onProductSelect: (ProductModel)->Unit) {
    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(0.25.dp, Color.LightGray),
        modifier =
        Modifier
            .padding(8.dp)
            .height(350.dp)
            .fillMaxWidth()
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .clickable(enabled = true, onClick = { onProductSelect(product) })
        ) {
            val (image, title, description,descriptionColumn) = createRefs()
            Image(
                contentScale = ContentScale.Crop,
                painter =
                rememberImagePainter(
                    data = product.attributes.featured_image_urls.firstOrNull(),
                    builder = {
                        placeholder(R.drawable.ic_launcher_foreground)
                        crossfade(true)
                    }
                ),
                contentDescription = "${product.attributes.name} Image",
                modifier =
                Modifier
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .height(150.dp)
                    .fillMaxWidth()
            )
            Text(
                text = product.attributes.name,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier =
                Modifier
                    .constrainAs(title) {
                        top.linkTo(image.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxWidth()
                    .absolutePadding(8.dp,8.dp,8.dp,0.dp)
            )
            Text(
                text = product.attributes.description.trim(),
                color = Color(0xFFFFC400),
                fontSize = 14.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                modifier =
                Modifier
                    .constrainAs(description) {
                        top.linkTo(title.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(descriptionColumn.top)
                        height = Dimension.fillToConstraints
                    }
                    .fillMaxWidth()
                    .absolutePadding(8.dp,8.dp,8.dp,0.dp)
            )
            Column(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .absolutePadding(8.dp,8.dp,8.dp,0.dp)
                    .constrainAs(descriptionColumn) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    },
                horizontalAlignment = Alignment.Start

            ) {

                Row {
                    if(product.attributes.original_price  != product.attributes.price){
                        Text(
                            text = "%,d".format(product.attributes.original_price),
                            color = Color.Black,
                            maxLines = 1,
                            modifier = Modifier.absolutePadding(top = 8.dp,right = 8.dp),
                            fontSize = 16.sp,
                            style = TextStyle(textDecoration = TextDecoration.LineThrough)
                        )
                    }
                    Text(
                        text = "%,d".format(product.attributes.price),
                        color = Color.Black,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.absolutePadding(top = 8.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
                RatingBar(
                    rating = product.attributes.rating.toFloat(),
                    modifier =
                    Modifier
                        .absolutePadding(top = 0.dp)
                        .height(12.dp),
                    color = Color(0xFFC71E9D)

                )
                Text(
                    text = "%d Variant(s)".format(product.attributes.variants_count),
                    color = Color.DarkGray,
                    modifier = Modifier.absolutePadding(top = 8.dp,bottom = 8.dp),
                    fontSize = 12.sp
                )


            }
        }
    }
}