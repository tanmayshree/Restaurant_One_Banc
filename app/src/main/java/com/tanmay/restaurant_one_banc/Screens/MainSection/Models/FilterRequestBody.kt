package com.tanmay.restaurant_one_banc.Screens.MainSection.Models

import kotlinx.serialization.Serializable

@Serializable
data class FilterRequestBody(
    val cuisine_type: ArrayList<String>? = null,
    val price_range: PriceRange? = null,
    val min_rating: Int? = null,
)

@Serializable
data class PriceRange(
    val min_amount: Int? = null,
    val max_amount: Int? = null,
)
