package com.tanmay.restaurant_one_banc.Screens.MainSection.Repository

import com.tanmay.restaurant_one_banc.Screens.MainSection.Models.FilterRequestBody
import com.tanmay.restaurant_one_banc.Screens.MainSection.Models.ItemListRequestBody
import com.tanmay.restaurant_one_banc.Screens.MainSection.Models.ItemListResponseBody
import com.tanmay.restaurant_one_banc.Screens.MainSection.Models.MakePaymentRequestBody
import com.tanmay.restaurant_one_banc.Screens.MainSection.Models.MakePaymentResponse
import com.tanmay.restaurant_one_banc.Utils.ApiResponseHandler
import com.tanmay.restaurant_one_banc.Utils.Resource
import com.tanmay.restaurant_one_banc.Utils.RetrofitClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainRepository {

    fun getItemsList(
        requestBody: ItemListRequestBody
    ): Flow<Resource<ItemListResponseBody>> = flow {
        emit(Resource.Loading())
        val response = ApiResponseHandler.handleApiCall {
            RetrofitClient.apiService("get_item_list").getItemsList(requestBody)
        }
        println("620555 Response: $response ${response.data}")
        emit(response)
    }

    fun getItemsByFilter(
        requestBody: FilterRequestBody
    ): Flow<Resource<ItemListResponseBody>> = flow {
        emit(Resource.Loading())
        val response = ApiResponseHandler.handleApiCall {
            RetrofitClient.apiService("get_item_by_filter").getItemsByFilter(requestBody)
        }
        println("620555 Response: $response ${response.data}")
        emit(response)
    }

    fun placeOrder(requestBody: MakePaymentRequestBody): Flow<Resource<MakePaymentResponse>> = flow {
        emit(Resource.Loading())
        val response = ApiResponseHandler.handleApiCall {
            RetrofitClient.apiService("make_payment").placeOrder(requestBody)
        }
        println("620555 Response: $response ${response.data}")
        emit(response)

    }
}