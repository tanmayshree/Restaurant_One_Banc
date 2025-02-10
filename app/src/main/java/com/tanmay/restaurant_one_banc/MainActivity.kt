package com.tanmay.restaurant_one_banc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.anonymous.ziwy.Screens.HomeSection.ViewModel.MainViewModel
import com.anonymous.ziwy.Screens.HomeSection.ViewModel.MainViewModelFactory
import com.tanmay.restaurant_one_banc.Room.MyApp
import com.tanmay.restaurant_one_banc.Screens.MainSection.Components.MainPage
import com.tanmay.restaurant_one_banc.ui.theme.Restaurant_One_BancTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Restaurant_One_BancTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val repository = (application as MyApp).repository

                    val viewModel = viewModel<MainViewModel>(
                        key = "MainViewModel",
                        factory = MainViewModelFactory(repository)
                    )

                    val state = viewModel.state.collectAsState().value

                    MainPage(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        viewModel = viewModel,
                        state = state
                    )
                }
            }
        }
    }
}
