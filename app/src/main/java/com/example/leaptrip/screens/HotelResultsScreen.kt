package com.example.leaptrip.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.leaptrip.R
import com.example.leaptrip.network.HotelResponse
import kotlin.math.round

@Composable
fun HotelResultsScreen(navController: NavController) {
    val viewModel: HotelViewModel = viewModel()
    val hotels by viewModel.hotels.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else if (hotels.isEmpty()) {
            Text(
                text = "Отели не найдены",
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(hotels) { hotel ->
                    HotelCard(hotel = hotel, context = context)
                }
            }
        }
    }
}

@Composable
fun HotelCard(hotel: HotelResponse, context: android.content.Context) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(hotel.booking_url))
            context.startActivity(intent)
        }
    ) {
        Column {
            // Заглушка вместо изображения (используем стандартную иконку)
            Image(
                painter = painterResource(id = R.drawable.ic_hotel),
                contentDescription = "Изображение отеля",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = hotel.name,
                    style = MaterialTheme.typography.headlineSmall
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_star),
                        contentDescription = "Рейтинг"
                    )
                    Text(
                        text = "${hotel.stars}★",
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Общая цена: ${hotel.price} ₽")
                Text(text = "Цена за ночь: ${hotel.price_per_night} ₽")

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_location),
                        contentDescription = "Местоположение"
                    )
                    Text(
                        text = "Широта: ${hotel.location.lat.roundTo(2)}, Долгота: ${hotel.location.lon.roundTo(2)}",
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }

                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(hotel.booking_url))
                        context.startActivity(intent)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Text("Забронировать")
                }
            }
        }
    }
}

fun Double.roundTo(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}