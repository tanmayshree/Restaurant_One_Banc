package com.tanmay.restaurant_one_banc.Screens.MainSection.Components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.anonymous.ziwy.Screens.HomeSection.ViewModel.MainStore
import com.anonymous.ziwy.Screens.HomeSection.ViewModel.MainViewModel
import com.tanmay.restaurant_one_banc.Screens.MainSection.Models.Cuisine
import com.tanmay.restaurant_one_banc.Screens.MainSection.Models.FilterRequestBody
import com.tanmay.restaurant_one_banc.Screens.SplashSection.SplashScreen
import kotlinx.coroutines.delay
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun MainPage(modifier: Modifier, viewModel: MainViewModel, state: MainStore) {
    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        println("620555 MainPage")
        viewModel.getItemsByFilter(FilterRequestBody(cuisine_type = arrayListOf()))
        viewModel.getAllCartItems()
        viewModel.getAllOrders()
    }

    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate("main") {
            popUpTo("splash") { inclusive = true }
        }
    }

    LaunchedEffect(state.isVisible) {
        if (state.isVisible) {
            navController.popBackStack()
        }
    }

    NavHost(
        navController = navController, startDestination = "splash",
        modifier = modifier
    ) {
        composable("splash") {
            SplashScreen()
        }
        composable("main") {
            HomePage(viewModel, state, navController)
        }

        composable(
            route = "cuisine_details/{cuisine_id}/{cuisine_name}",
            arguments = listOf(
                navArgument("cuisine_id") {
                    type = NavType.StringType
                    nullable = true
                },
                navArgument("cuisine_name") {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) {
            Column {
                val cuisineId = it.arguments?.getString("cuisine_id")
                val cuisineName = it.arguments?.getString("cuisine_name")
                if (cuisineId != null && cuisineName != null) {
                    val itemsList =
                        state.cuisineItemsMap[Cuisine(cuisineId, cuisineName)]?.toList()?.map {
                            it.copy(
                                quantity = state.cartItems.find { cartItem -> cartItem.itemId == it.id }?.quantity
                                    ?: 0
                            )
                        }
                    CuisineDetailsPage(
                        viewModel = viewModel,
                        state = state,
                        navController = navController,
                        cuisine = Cuisine(cuisineId, cuisineName),
                        itemsList = itemsList ?: emptyList()
                    )
                }
            }
        }

        composable("place_order") {
            CartPage(viewModel, state, navController)
        }
    }

}

fun formatTimestamp(timestamp: String): Pair<String, String> {
    val cleanedTimestamp = timestamp.substringBefore(".")

    val instant = Instant.parse("${cleanedTimestamp}Z")

    val zoneId = ZoneId.systemDefault()
    val date = instant.atZone(zoneId).toLocalDate()
    val time = instant.atZone(zoneId).toLocalTime()

    val dateFormatter = DateTimeFormatter.ofPattern("d MMMM, yyyy", Locale.ENGLISH)
    val formattedDate = date.format(dateFormatter)

    val formattedTime = time.toString().substring(0, 5)

    return Pair(formattedDate, formattedTime)
}