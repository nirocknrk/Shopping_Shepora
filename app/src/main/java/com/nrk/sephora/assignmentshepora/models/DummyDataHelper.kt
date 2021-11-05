package com.nrk.sephora.assignmentshepora.models

object DummyDataHelper {

    fun createDummyProduct(
        name:String,
        description:String,
        originalPrice:Int = 22000,
        price:Int = 22000,
        rating:Double = 4.5,
        reviewCount:Int = 2,
        variantsCount:Int = 2,
        heading:String = "heading",
        howTo:String = "how to",
        howToText:String = "how to text",
        soldOut:Boolean = false,
        underSale:Boolean = true,
        id:String = List(7) {('a'..'z') + ('A'..'Z') + ('0'..'9').random()}.joinToString {""}) =
        ProductModel(
            attributes = Attributes(
                "",
                listOf(),
                description,
                listOf("", ""),
                0,
                heading,
                howTo,
                howToText,
                listOf(),
                listOf(),
                "",
                listOf(),
                name,
                true,
                originalPrice,
                price,
                rating,
                reviewCount,
                Seo("", ""),
                soldOut,
                underSale,
                variantsCount,
                ""
            ),
            id = id,
            type = "DummyType",
        )
    }

