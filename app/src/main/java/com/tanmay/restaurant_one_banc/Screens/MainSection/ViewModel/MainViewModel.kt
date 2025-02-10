package com.anonymous.ziwy.Screens.HomeSection.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanmay.restaurant_one_banc.Room.CartItem
import com.tanmay.restaurant_one_banc.Room.OrderItem
import com.tanmay.restaurant_one_banc.Room.RoomRepository
import com.tanmay.restaurant_one_banc.Screens.MainSection.Models.Cuisine
import com.tanmay.restaurant_one_banc.Screens.MainSection.Models.FilterRequestBody
import com.tanmay.restaurant_one_banc.Screens.MainSection.Models.ItemListRequestBody
import com.tanmay.restaurant_one_banc.Screens.MainSection.Models.Items
import com.tanmay.restaurant_one_banc.Screens.MainSection.Models.MakePaymentRequestBody
import com.tanmay.restaurant_one_banc.Screens.MainSection.Repository.MainRepository
import com.tanmay.restaurant_one_banc.Utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class MainViewModel(
    private val roomRepository: RoomRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MainStore())
    val state: StateFlow<MainStore> = _state

    private val repository = MainRepository()

    private fun getItemsList(requestBody: ItemListRequestBody) {
        viewModelScope.launch {
            repository.getItemsList(requestBody).collect {
                when (it) {
                    is Resource.Error -> {
                        println("620555 Error: ${it.message}")
                    }

                    is Resource.Loading -> {
                        println("620555 Loading")
                    }

                    is Resource.Success -> {
                        val newMap = _state.value.cuisineItemsMap

                        it.data?.cuisines?.forEach { item ->
                            val cuisine = Cuisine(item.cuisine_id, item.cuisine_name)
                            item.items?.let { it1 ->
                                newMap.computeIfAbsent(cuisine) { mutableSetOf() }
                                    .addAll(it1.map { it.copy(cuisine_id = cuisine.cuisine_id) })
                            }
                        }

                        _state.value = _state.value.copy(
                            cuisineItemsMap = newMap,
                        )

                        if ((requestBody.page ?: 0) < (it.data?.total_pages ?: 0)) {
                            getItemsList(
                                ItemListRequestBody(
                                    (requestBody.page ?: 0) + 1,
                                    requestBody.count ?: 10
                                )
                            )
                        } else {
                            /*val topDishesList = _state.value.cuisineItemsMap.values.flatten()
                                .sortedByDescending { it.rating?.toDoubleOrNull() ?: 0.0 }.take(3)
                            //get first 3
                            _state.value = _state.value.copy(
                                topDishesList = ArrayList(topDishesList)
                            )*/
                        }
                    }
                }
            }
        }
    }

    fun getItemsByFilter(requestBody: FilterRequestBody) {
        viewModelScope.launch {
            repository.getItemsByFilter(requestBody).collect {
                when (it) {
                    is Resource.Error -> {
                        println("620555 Error: ${it.message}")
                    }

                    is Resource.Loading -> {
                        println("620555 Loading")
                    }

                    is Resource.Success -> {

                        val updatedMap = mutableMapOf<Cuisine, MutableSet<Items>>()

                        it.data?.cuisines?.forEach { item ->
                            val cuisine = Cuisine(item.cuisine_id, item.cuisine_name)
                            updatedMap[cuisine] = mutableSetOf()
                        }

                        _state.value = _state.value.copy(
                            cuisinesList = it.data?.cuisines,
                            cuisineItemsMap = updatedMap
                        )

                        getItemsList(ItemListRequestBody(1, 10))
                    }
                }
            }
        }
    }

    fun placeOrder(requestBody: MakePaymentRequestBody) {
        viewModelScope.launch {
            repository.placeOrder(requestBody).collect {
                when (it) {
                    is Resource.Error -> {
                        println("620555 Error: ${it.message}")
                    }

                    is Resource.Loading -> {
                        println("620555 Loading")
                    }

                    is Resource.Success -> {
                        println("620555 Success: ${it.data}")
                        insertOrderToDb(
                            OrderItem(
                                orderId = 0,
                                txnRefNumber = it.data?.txn_ref_no ?: "",
                                txnDateTime = LocalDateTime.now().toString(),
                                orderDetail = requestBody
                            )
                        )
                        _state.value = _state.value.copy(
                            isVisible = true
                        )
                    }
                }
            }
        }
    }



    fun dismissAlert() {
        _state.value = _state.value.copy(
            isVisible = false
        )
        clearCart()
        getAllOrders()
    }

    fun insertItemToCart(item: CartItem) {
        viewModelScope.launch {
            roomRepository.insertItemToCart(item)
            getAllCartItems()
        }
    }

    fun getAllCartItems() {
        viewModelScope.launch {
            val cartItems = roomRepository.getAllCartItems()
            _state.value = _state.value.copy(
                cartItems = cartItems
            )
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            roomRepository.clearCart()
        }
    }

    fun insertOrderToDb(order: OrderItem) {
        viewModelScope.launch {
            roomRepository.insertOrderToDb(order)
        }
    }

    fun getAllOrders() {
        viewModelScope.launch {
            val orders = roomRepository.getAllOrders()
            _state.value = _state.value.copy(
                orderList = orders
            )
        }
    }
}