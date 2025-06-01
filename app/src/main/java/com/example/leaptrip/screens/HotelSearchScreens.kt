package com.example.leaptrip.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.leaptrip.viewmodel.HotelViewModel

@Composable
fun HotelSearchScreen(
    navController: NavHostController,
    hotelViewModel: HotelViewModel = viewModel()
) {
    var city by remember { mutableStateOf("") }
    var checkIn by remember { mutableStateOf("") }
    var checkOut by remember { mutableStateOf("") }
    var adults by remember { mutableStateOf("2") }
    var stars by remember { mutableStateOf("") }  // Например "3,4,5"
    var priceMin by remember { mutableStateOf("") }
    var priceMax by remember { mutableStateOf("") }

    val isLoading by hotelViewModel.isLoading.collectAsState()
    val error by hotelViewModel.error.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("Город") }
        )
        OutlinedTextField(
            value = checkIn,
            onValueChange = { checkIn = it },
            label = { Text("Дата заезда (YYYY-MM-DD)") }
        )
        OutlinedTextField(
            value = checkOut,
            onValueChange = { checkOut = it },
            label = { Text("Дата выезда (YYYY-MM-DD)") }
        )
        OutlinedTextField(
            value = adults,
            onValueChange = { adults = it.filter { c -> c.isDigit() } },
            label = { Text("Количество взрослых") }
        )
        OutlinedTextField(
            value = stars,
            onValueChange = { stars = it },
            label = { Text("Звезды (через запятую, например: 3,4,5)") }
        )
        OutlinedTextField(
            value = priceMin,
            onValueChange = { priceMin = it.filter { c -> c.isDigit() } },
            label = { Text("Мин. цена") }
        )
        OutlinedTextField(
            value = priceMax,
            onValueChange = { priceMax = it.filter { c -> c.isDigit() } },
            label = { Text("Макс. цена") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val starsList = stars.split(",").mapNotNull {
                    val num = it.trim().toIntOrNull()
                    if (num != null) num else null
                }.takeIf { it.isNotEmpty() }

                hotelViewModel.searchHotels(
                    city = city,
                    checkIn = checkIn,
                    checkOut = checkOut,
                    adults = adults.toIntOrNull() ?: 2,
                    stars = starsList,
                    priceMin = priceMin.toIntOrNull(),
                    priceMax = priceMax.toIntOrNull()
                )
                navController.navigate("hotelResults")
            },
            enabled = !isLoading
        ) {
            Text("Поиск")
        }

        if (isLoading) {
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator()
        }

        if (error != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = error ?: "", color = MaterialTheme.colorScheme.error)
        }
    }
}
