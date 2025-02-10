package com.tanmay.restaurant_one_banc.Screens.MainSection.Components

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.anonymous.ziwy.Screens.HomeSection.ViewModel.MainStore
import com.anonymous.ziwy.Screens.HomeSection.ViewModel.MainViewModel
import com.tanmay.restaurant_one_banc.Room.CartItem
import com.tanmay.restaurant_one_banc.Screens.MainSection.Models.Data
import com.tanmay.restaurant_one_banc.Screens.MainSection.Models.Items
import com.tanmay.restaurant_one_banc.Screens.MainSection.Models.MakePaymentRequestBody

@Composable
fun CustomTopAppBar(navController: NavHostController, text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.secondary
                    )
                ),
                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Back Button with Stylish Look
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.2f))
                    .border(1.dp, Color.White.copy(alpha = 0.3f), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            // Title with Balanced Spacing
            Text(
                text = text,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = Color.White,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp) // Ensure spacing from back button
            )
        }
    }
}


@Composable
fun CartPage(viewModel: MainViewModel, state: MainStore, navController: NavHostController) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CustomTopAppBar(navController, "Cart")
        },
        bottomBar = {
            CartSummarySection(state, viewModel)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            val finalCartList = state.cartItems.mapNotNull { cartItem ->
                if (cartItem.quantity > 0) {
                    state.cuisineItemsMap.keys.find { it.cuisine_id == cartItem.cuisineId }
                        ?.let { cuisine ->
                            state.cuisineItemsMap[cuisine]?.find { it.id == cartItem.itemId }
                                ?.copy(quantity = cartItem.quantity)
                        }
                } else null
            }.sortedBy { it.name }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(horizontal = 12.dp)
            ) {
                items(finalCartList) { item ->
                    CartItemCard(item, viewModel)
                }
            }
        }
    }
}

@Composable
fun CartItemCard(item: Items, viewModel: MainViewModel) {
    val animatedQuantity by animateIntAsState(
        targetValue = item.quantity,
        animationSpec = tween(durationMillis = 300)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .shadow(6.dp, shape = RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Item Image
            AsyncImage(
                model = item.image_url,
                contentDescription = item.name,
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(12.dp))

            // Item Details
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.name ?: "",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "₹${item.price ?: "0"}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "⭐ ${item.rating ?: "N/A"}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Quantity Controls
                QuantityControl(item, viewModel, animatedQuantity)
            }
        }
    }
}

@Composable
fun QuantityControl(item: Items, viewModel: MainViewModel, animatedQuantity: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        IconButton(
            onClick = {
                if (item.quantity > 0 && item.id != null && item.cuisine_id != null) {
                    viewModel.insertItemToCart(
                        CartItem(
                            itemId = item.id,
                            cuisineId = item.cuisine_id,
                            quantity = item.quantity - 1
                        )
                    )
                }
            },
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.error.copy(alpha = 0.15f)),
        ) {
            Text(
                text = "——",
                letterSpacing = (-2).sp,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.labelLarge.fontSize
            )
        }

        Text(
            text = animatedQuantity.toString(),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        IconButton(
            onClick = {
                if (item.id != null && item.cuisine_id != null) {
                    viewModel.insertItemToCart(
                        CartItem(
                            itemId = item.id,
                            cuisineId = item.cuisine_id,
                            quantity = item.quantity + 1
                        )
                    )
                }
            },
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Increase",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}


@Composable
fun CartSummarySection(
    state: MainStore,
    viewModel: MainViewModel
) {
    val finalCartList = state.cartItems.mapNotNull { cartItem ->
        if (cartItem.quantity > 0) {
            state.cuisineItemsMap.keys.find { it.cuisine_id == cartItem.cuisineId }
                ?.let { cuisine ->
                    state.cuisineItemsMap[cuisine]?.find { it.id == cartItem.itemId }
                        ?.copy(quantity = cartItem.quantity)
                }
        } else null
    }

    val totalAmount = finalCartList.sumOf { (it.price?.toDoubleOrNull() ?: 0.0) * it.quantity }
    val cgst = String.format("%.2f", totalAmount * 0.025).toDouble()
    val sgst = String.format("%.2f", totalAmount * 0.025).toDouble()
    val totalAmountWithTax = String.format("%.2f", totalAmount + cgst + sgst).toDouble()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 10.dp, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
        ) {
            // Title
            Text(
                text = "Order Summary",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))
            Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)
            Spacer(modifier = Modifier.height(8.dp))

            // Subtotal
            SummaryRow(title = "Subtotal", value = "₹$totalAmount")

            // Taxes
            SummaryRow(title = "CGST (2.5%)", value = "₹$cgst", textStyle = MaterialTheme.typography.bodyMedium)
            SummaryRow(title = "SGST (2.5%)", value = "₹$sgst", textStyle = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(8.dp))
            Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)
            Spacer(modifier = Modifier.height(8.dp))

            // Total
            SummaryRow(
                title = "Total (Incl. Taxes)",
                value = "₹$totalAmountWithTax",
                textStyle = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                textColor = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Place Order Button
            Button(
                enabled = finalCartList.isNotEmpty(),
                onClick = {
                    viewModel.placeOrder(
                        MakePaymentRequestBody(
                            total_amount = totalAmountWithTax.toString(),
                            total_items = finalCartList.sumOf { it.quantity },
                            data = finalCartList.map {
                                Data(
                                    cuisine_id = it.cuisine_id ?: "",
                                    item_id = it.id ?: "",
                                    item_price = it.price ?: "0",
                                    item_quantity = it.quantity.toString(),
                                    item_name = it.name ?: "",
                                    itemUrl = it.image_url ?: ""
                                )
                            }
                        )
                    )
//                    navController.popBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .clip(RoundedCornerShape(12.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    "Place Order",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun SummaryRow(
    title: String,
    value: String,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    textColor: Color = MaterialTheme.colorScheme.onSurface
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title, style = textStyle, color = textColor)
        Text(text = value, style = textStyle.copy(fontWeight = FontWeight.Bold), color = textColor)
    }
}


