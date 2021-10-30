package com.nrk.sephora.assignmentshepora.models

import com.google.gson.annotations.SerializedName


data class BaseProductResponse(
    val data: List<ProductModel>,
    val links: Links,
    val meta: Meta
)

data class ProductModel(
    @SerializedName("attributes") val attributes: Attributes,
    @SerializedName("id") val id: String,
    @SerializedName("type") val type: String
)

data class Links(
    @SerializedName("last") val last: String,
    @SerializedName("next") val next: String,
    @SerializedName("self")  val self: String
)

data class Meta(
    @SerializedName("current-page") val current_page: Int,
    @SerializedName("per-page") val per_page: Int,
    @SerializedName("total") val total: Int,
    @SerializedName("total-items") val total_items: Int,
    @SerializedName("total-pages") val total_pages: Int
)

data class Attributes(
    @SerializedName("benefits") val benefits: String,
    @SerializedName("default-image-urls")val default_image_urls: List<String>,
    @SerializedName("description") val description: String,
    @SerializedName("featured-image-urls")val featured_image_urls: List<String>,
    @SerializedName("featured-variant-id")val featured_variant_id: Int,
    @SerializedName("heading") val heading: String,
    @SerializedName("how-to")val how_to: String,
    @SerializedName("how-to-text")val how_to_text: String,
    @SerializedName("icon-image-urls")val icon_image_urls: List<String>,
    @SerializedName("image-urls")val image_urls: List<String>,
    @SerializedName("ingredients") val ingredients: String,
    @SerializedName("labels") val labels: List<Any>,
    @SerializedName("name") val name: String,
    @SerializedName("new-arrival")val new_arrival: Boolean,
    @SerializedName("original-price") val original_price: Int,
    @SerializedName("price")val price: Int,
    @SerializedName("rating") val rating: Double,
    @SerializedName("reviews-count")val reviews_count: Int,
    @SerializedName("sale-text")val sale_text: Any,
    @SerializedName("seo") val seo: Seo,
    @SerializedName("sold-out")val sold_out: Boolean,
    @SerializedName("under-sale")val under_sale: Boolean,
    @SerializedName("variants-count")val variants_count: Int,
    @SerializedName("web-ur")val web_url: String,
) {
    constructor(name: String,description: String) : this(
        "",
        listOf(),
        description,
        emptyList(),
        0,
        "",
        "",
        "",
        listOf(),
        listOf(),
        "",
        listOf(),
        name,
        true,
        22,
        20,
        4.5,
        2,
        "",
        Seo("",""),
        false,
        true,
        0,
        ""

        ) {

    }
}

data class Seo(
    @SerializedName("meta-description") val meta_description: String,
    @SerializedName("page-title:")  val page_title: String
)
