package com.tanmay.restaurant_one_banc.Screens.MainSection.Models

import kotlinx.serialization.Serializable

@Serializable
data class ItemListRequestBody(
    val page: Int? = null,
    val count: Int? = null
)
