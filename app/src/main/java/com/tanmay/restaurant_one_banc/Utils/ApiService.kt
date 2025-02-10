package com.tanmay.restaurant_one_banc.Utils

import com.tanmay.restaurant_one_banc.Screens.MainSection.Models.FilterRequestBody
import com.tanmay.restaurant_one_banc.Screens.MainSection.Models.ItemListRequestBody
import com.tanmay.restaurant_one_banc.Screens.MainSection.Models.ItemListResponseBody
import com.tanmay.restaurant_one_banc.Screens.MainSection.Models.MakePaymentRequestBody
import com.tanmay.restaurant_one_banc.Screens.MainSection.Models.MakePaymentResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/emulator/interview/get_item_list")
    suspend fun getItemsList(
        @Body requestBody: ItemListRequestBody
    ): Response<ItemListResponseBody>

    @POST("/emulator/interview/get_item_by_filter")
    suspend fun getItemsByFilter(
        @Body requestBody: FilterRequestBody
    ): Response<ItemListResponseBody>

    @POST("/emulator/interview/make_payment")
    suspend fun placeOrder(
        @Body requestBody: MakePaymentRequestBody
    ): Response<MakePaymentResponse>
}
