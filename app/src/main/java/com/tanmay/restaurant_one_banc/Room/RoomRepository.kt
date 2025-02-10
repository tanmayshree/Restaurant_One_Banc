package com.tanmay.restaurant_one_banc.Room

class RoomRepository(private val dao: AppDao) {

    suspend fun insertItemToCart(item: CartItem) {
        dao.insertItemToCart(item)
    }

    suspend fun getAllCartItems(): List<CartItem> {
        return dao.getAllCartItems()
    }

    suspend fun clearCart() {
        dao.clearCart()
    }

    suspend fun insertOrderToDb(order: OrderItem) {
        dao.insertOrderToDb(order)
    }

    suspend fun getAllOrders(): List<OrderItem> {
        return dao.getAllOrders()
    }
}
