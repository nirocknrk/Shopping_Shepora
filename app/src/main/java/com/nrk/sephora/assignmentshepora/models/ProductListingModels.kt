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
    @SerializedName("benefits") val benefits: String = String(),
    @SerializedName("default-image-urls")val default_image_urls: List<String> = listOf(),
    @SerializedName("description") val description: String = String(),
    @SerializedName("featured-image-urls")val featured_image_urls: List<String> = listOf(),
    @SerializedName("featured-variant-id")val featured_variant_id: Int =0,
    @SerializedName("heading") val heading: String = String(),
    @SerializedName("how-to")val how_to: String = String(),
    @SerializedName("how-to-text")val how_to_text: String = String(),
    @SerializedName("icon-image-urls")val icon_image_urls: List<String> = listOf(),
    @SerializedName("image-urls")val image_urls: List<String> = listOf(),
    @SerializedName("ingredients") val ingredients: String = String(),
    @SerializedName("labels") val labels: List<Any> = listOf(),
    @SerializedName("name") val name: String = String(),
    @SerializedName("new-arrival")val new_arrival: Boolean = false,
    @SerializedName("original-price") val original_price: Int = 0,
    @SerializedName("price")val price: Int = 0,
    @SerializedName("rating") val rating: Double = 0.0,
    @SerializedName("reviews-count")val reviews_count: Int = 0,
    @SerializedName("seo") val seo: Seo = Seo("",""),
    @SerializedName("sold-out")val sold_out: Boolean = false,
    @SerializedName("under-sale")val under_sale: Boolean = false,
    @SerializedName("variants-count")val variants_count: Int = 0,
    @SerializedName("web-ur")val web_url: String,
)

data class Seo(
    @SerializedName("meta-description") val meta_description: String,
    @SerializedName("page-title:")  val page_title: String
)
