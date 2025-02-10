package com.tanmay.restaurant_one_banc.Room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tanmay.restaurant_one_banc.Screens.MainSection.Models.MakePaymentRequestBody

@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey val itemId: String,
    val cuisineId: String,
    val quantity: Int = 0
)

@Entity(tableName = "order_history")
data class OrderItem(
    @PrimaryKey(autoGenerate = true) val orderId: Int,
    val txnRefNumber: String,
    val txnDateTime: String,
    val orderDetail: MakePaymentRequestBody
)
