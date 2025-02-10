package com.tanmay.restaurant_one_banc.Screens.MainSection.Models

import kotlinx.serialization.Serializable

@Serializable
data class MakePaymentRequestBody(
    val total_amount: String,
    val total_items: Int,
    val data: List<Data>
)

@Serializable
data class Data(
    val cuisine_id: String,
    val item_id: String,
    val item_price: String,
    val item_quantity: String,

    val item_name: String,
    val itemUrl: String
)