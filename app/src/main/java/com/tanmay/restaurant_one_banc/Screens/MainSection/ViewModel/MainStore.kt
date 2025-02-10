package com.anonymous.ziwy.Screens.HomeSection.ViewModel

import com.tanmay.restaurant_one_banc.Room.CartItem
import com.tanmay.restaurant_one_banc.Room.OrderItem
import com.tanmay.restaurant_one_banc.Screens.MainSection.Models.Cuisine
import com.tanmay.restaurant_one_banc.Screens.MainSection.Models.Cuisines
import com.tanmay.restaurant_one_banc.Screens.MainSection.Models.Items
import com.tanmay.restaurant_one_banc.Screens.MainSection.Models.LoadingScreenState

data class MainStore(
    val isLoading: Boolean? = null,
    val loadingScreenState: LoadingScreenState? = null,
    val message: String? = null,

    val cuisinesList: ArrayList<Cuisines>? = null,
//    val topDishesList: ArrayList<Items>? = null,

    val cuisineItemsMap: MutableMap<Cuisine, MutableSet<Items>> = mutableMapOf(),
    val cartItems: List<CartItem> = emptyList(),
    val orderList: List<OrderItem> = emptyList(),
    val isVisible: Boolean = false
)
