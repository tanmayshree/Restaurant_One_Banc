package com.tanmay.restaurant_one_banc.Screens.MainSection.Models

import kotlinx.serialization.Serializable

@Serializable
data class ItemListResponseBody(
    val response_code: Int? = null,
    val outcome_code: Int? = null,
    val response_message: String? = null,
    val page: Int? = null,
    val count: Int? = null,
    val total_pages: Int? = null,
    val total_items: Int? = null,
    val cuisines: ArrayList<Cuisines>? = null
)

@Serializable
data class Items(
    val id: String? = null,
    val name: String? = null,
    val image_url: String? = null,
    val price: String? = null,
    val rating: String? = null,
    val quantity: Int = 0,
    val cuisine_id: String? = null,
)

@Serializable
data class Cuisines(
    val cuisine_id: String? = null,
    val cuisine_name: String? = null,
    val cuisine_image_url: String? = null,
    val items: ArrayList<Items>? = null
)

@Serializable
data class Cuisine(
    val cuisine_id: String? = null,
    val cuisine_name: String? = null,
)
