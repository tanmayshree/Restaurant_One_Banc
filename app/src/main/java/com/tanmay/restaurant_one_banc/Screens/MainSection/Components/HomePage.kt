package com.tanmay.restaurant_one_banc.Screens.MainSection.Components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.anonymous.ziwy.Screens.HomeSection.ViewModel.MainStore
import com.anonymous.ziwy.Screens.HomeSection.ViewModel.MainViewModel
import com.tanmay.restaurant_one_banc.Room.CartItem
import com.tanmay.restaurant_one_banc.Room.OrderItem
import com.tanmay.restaurant_one_banc.Screens.MainSection.Models.Cuisine
import com.tanmay.restaurant_one_banc.Screens.MainSection.Models.Cuisines
import com.tanmay.restaurant_one_banc.Screens.MainSection.Models.Items
import kotlinx.coroutines.delay

@Composable
fun HomePage(viewModel: MainViewModel, state: MainStore, navController: NavController) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CustomAppBar(navController)
        }
    ) { innerPadding ->
        val cuisines = state.cuisinesList ?: emptyList()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {

            if (cuisines.isNotEmpty()) {
                CuisineCarousel(cuisines, navController)
            } else {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .height(250.dp)
                        .fillMaxWidth()
                        .padding(8.dp),
                    elevation = CardDefaults.elevatedCardElevation()
                ) {
                    ShimmerEffect(250.dp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            TopDishesSection(viewModel, state)

            Spacer(modifier = Modifier.height(16.dp))

            OrderHistorySection(state.orderList)
        }

        if (state.isVisible) {
            AlertDialog(
                onDismissRequest = { viewModel.dismissAlert() },
                confirmButton = {
                    Button(onClick = { viewModel.dismissAlert() }) {
                        Text("OK", color = Color.White)
                    }
                },
                title = { Text("Success!", fontWeight = FontWeight.Bold) },
                text = { Text("Order placed successfully!") }
            )
        }
    }
}

@Composable
fun CustomAppBar(navController: NavController) {
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
                shape = RoundedCornerShape(bottomEnd = 24.dp, bottomStart = 24.dp)
            ),
//            .shadow(elevation = 8.dp, shape = RoundedCornerShape(bottomEnd = 24.dp, bottomStart = 24.dp)),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Profile + Title
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    color = Color.White,
                    shape = CircleShape,
                    shadowElevation = 6.dp,
                    modifier = Modifier.size(42.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = "OB",
                            color = Color.Black,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = "Restaurant",
                        color = Color.White.copy(alpha = 0.8f),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "One Banc",
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Cart Icon Button
            IconButton(
                onClick = { navController.navigate("place_order") },
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.2f))
                    .border(1.dp, Color.White.copy(alpha = 0.5f), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Cart",
                    tint = Color.White,
                    modifier = Modifier.size(26.dp)
                )
            }
        }
    }
}


@Composable
fun CuisineCarousel(cuisines: List<Cuisines>, navController: NavController) {
    val infiniteCuisines = remember { List(1000) { cuisines[it % cuisines.size] } }
    val pagerState = rememberPagerState(initialPage = 500) { infiniteCuisines.size }

    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    ) { page ->
        CuisineItem(infiniteCuisines[page]) {
            navController.navigate("cuisine_details/${infiniteCuisines[page].cuisine_id}/${infiniteCuisines[page].cuisine_name}")
        }
    }

    // Auto-scroll effect
    LaunchedEffect(pagerState) {
        while (true) {
            pagerState.animateScrollToPage(pagerState.currentPage + 1, animationSpec = tween(1000))
            delay(3500)
        }
    }
}

@Composable
fun CuisineItem(cuisine: Cuisines, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .height(250.dp)
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.elevatedCardElevation()
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomStart
        ) {
            AsyncImage(
                model = cuisine.cuisine_image_url,
                contentDescription = cuisine.cuisine_name,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f)),
                            startY = 0.5f
                        )
                    )
            )
            Text(
                text = cuisine.cuisine_name ?: "",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )
        }
    }
}

@Composable
fun TopDishesSection(viewModel: MainViewModel, state: MainStore) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Top 3 Famous Dishes",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        val dishes = state.cuisineItemsMap.values.flatten()
            .sortedByDescending { it.rating?.toDoubleOrNull() ?: 0.0 }.take(3).map {
                it.copy(
                    quantity = state.cartItems.find { cartItem -> cartItem.itemId == it.id }?.quantity
                        ?: 0
                )
            }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (dishes.isEmpty()) {
                // Show shimmer effect for loading state
                items(3) {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .width(180.dp)
                            .padding(8.dp),
                        elevation = CardDefaults.elevatedCardElevation(8.dp),
                        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        ShimmerEffect(200.dp)
                    }
                }
            } else {
                items(dishes) { dish ->
                    DishTile(dish, viewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DishTile(dish: Items, viewModel: MainViewModel) {

    ElevatedCard(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .width(180.dp)
            .padding(8.dp),
        elevation = CardDefaults.elevatedCardElevation(8.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Box(modifier = Modifier.height(120.dp)) {
                AsyncImage(
                    model = dish.image_url,
                    contentDescription = dish.name,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )

                // Badge for rating
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(6.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(horizontal = 6.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "⭐ ${dish.rating ?: "-"}",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Dish Name
            Text(
                text = dish.name ?: "",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Price
            Text(
                text = "₹${dish.price ?: "N/A"}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Quantity
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        if (dish.quantity > 0 && dish.id != null && dish.cuisine_id != null) {
                            viewModel.insertItemToCart(
                                item = CartItem(
                                    itemId = dish.id,
                                    cuisineId = dish.cuisine_id,
                                    quantity = dish.quantity - 1
                                )
                            )
                        }
                    },
                    colors = IconButtonDefaults.iconButtonColors(MaterialTheme.colorScheme.error)
                ) {
                    Text(
                        text = "——",
                        letterSpacing = (-2).sp,
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.labelLarge.fontSize
                    )
                }

                AnimatedContent(
                    targetState = dish.quantity.toString(),
                    transitionSpec = {
                        (slideInVertically { it } + fadeIn()).togetherWith(slideOutVertically { -it } + fadeOut())
                    }
                ) { count ->
                    Text(
                        text = count,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                }

                IconButton(
                    onClick = {
                        if (dish.id != null && dish.cuisine_id != null) {
                            viewModel.insertItemToCart(
                                item = CartItem(
                                    itemId = dish.id,
                                    cuisineId = dish.cuisine_id,
                                    quantity = dish.quantity + 1
                                )
                            )
                        }
                    },
                    colors = IconButtonDefaults.iconButtonColors(MaterialTheme.colorScheme.primary)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Increase",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun OrderHistorySection(orderList: List<OrderItem>) {
    Column(modifier = Modifier.padding(16.dp)) {
        val isDropdownExpanded = remember { mutableStateOf(false) }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Order History",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp),
                color = Color.Black
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Delete",
                tint = Color.Black,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        isDropdownExpanded.value = !isDropdownExpanded.value
                    }
                    .rotate(if (isDropdownExpanded.value) 180f else 0f)
            )
        }

        if (!isDropdownExpanded.value) return@Column

        orderList.reversed().forEachIndexed { index, order ->
            val isExpanded =
                remember { mutableStateOf(false) }

            if (index != 0) {
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    thickness = 1.dp,
                    color = Color.LightGray.copy(alpha = 0.3f)
                )
            }

            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = CardDefaults.elevatedCardElevation(8.dp),
                colors = CardDefaults.cardColors(Color(0xFFF9F9F9))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    // Transaction Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Transaction Ref. No.:",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                        Text(
                            text = order.txnRefNumber,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Amount Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Amount:",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                        Text(
                            text = "₹${order.orderDetail.total_amount}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Date Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Date:",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                        Text(
                            text = "${formatTimestamp(order.txnDateTime).first}, ${
                                formatTimestamp(order.txnDateTime).second
                            }",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Conditionally Expandable Items Section
                    if (order.orderDetail.data.isNotEmpty()) {
                        // View More / View Less Button
                        TextButton(
                            onClick = { isExpanded.value = !isExpanded.value },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = if (isExpanded.value) "View Less" else "View More",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        if (isExpanded.value) {
                            // Items Title
                            Text(
                                text = "Items:",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            // Iterate through each item in the order
                            order.orderDetail.data.forEachIndexed { index, item ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = "${index + 1}.",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.Gray
                                    )
                                    Text(
                                        text = "${item.item_name} x ${item.item_quantity}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // LazyRow for item images
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                items(order.orderDetail.data) { item ->
                                    Card(
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier.size(80.dp),
                                        elevation = CardDefaults.elevatedCardElevation(4.dp),
                                        colors = CardDefaults.cardColors(Color.LightGray)
                                    ) {
                                        AsyncImage(
                                            model = item.itemUrl,
                                            contentDescription = item.item_name,
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ShimmerEffect(height: Dp) {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f)
    )
    val transition = rememberInfiniteTransition()
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateAnim.value, translateAnim.value),
        end = Offset(translateAnim.value + 500, translateAnim.value + 500)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .background(brush = brush)
    )
}