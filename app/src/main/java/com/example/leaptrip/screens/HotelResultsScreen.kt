package com.example.leaptrip.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.leaptrip.viewmodel.HotelViewModel

@Composable
fun HotelResultsScreen(
    navController: NavHostController,
    hotelViewModel: HotelViewModel = viewModel()
) {
    val hotels = hotelViewModel.hotels.collectAsState().value
    val isLoading = hotelViewModel.isLoading.collectAsState().value
    val error = hotelViewModel.error.collectAsState().value
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        when {
            isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            error?.contains("404") == true || (error != null && error.equals("Отели не найдены", true)) -> {
                Text(
                    text = "Отели не найдены",
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            error != null -> {
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            hotels.isEmpty() -> {
                Text(
                    text = "Отели не найдены",
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            else -> {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(hotels) { hotel ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .clickable {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(hotel.booking_url))
                                    context.startActivity(intent)
                                }
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(text = hotel.name, style = MaterialTheme.typography.titleMedium)
                                Text(text = "Звезды: ${hotel.stars}")
                                Text(text = "Цена: ${hotel.price} ₽ (всего), ${hotel.price_per_night} ₽ за ночь")
                            }
                        }
                    }
                }
            }
        }
    }
}
