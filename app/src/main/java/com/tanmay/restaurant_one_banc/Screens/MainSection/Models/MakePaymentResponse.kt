package com.tanmay.restaurant_one_banc.Screens.MainSection.Models

import kotlinx.serialization.Serializable

@Serializable
data class MakePaymentResponse(
    val response_code: Int? = null,
    val outcome_code: Int? = null,
    val response_message: String? = null,
    val txn_ref_no: String? = null
)

