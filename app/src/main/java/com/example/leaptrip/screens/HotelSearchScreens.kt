package com.example.leaptrip.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun HotelSearchScreen(navController: NavController) {
    val viewModel: HotelViewModel = viewModel()
    val error by viewModel.error.collectAsState()

    var city by remember { mutableStateOf("") }
    var checkIn by remember { mutableStateOf("") }
    var checkOut by remember { mutableStateOf("") }
    var adults by remember { mutableStateOf("2") }
    var selectedStars by remember { mutableStateOf<List<Int>>(emptyList()) }
    var priceMin by remember { mutableStateOf("") }
    var priceMax by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Основные поля
        OutlinedTextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("Город") },
            modifier = Modifier.fillMaxWidth()
        )

        // Даты заезда/выезда
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = checkIn,
                onValueChange = { checkIn = it },
                label = { Text("Заезд (ГГГГ-ММ-ДД)") },
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = checkOut,
                onValueChange = { checkOut = it },
                label = { Text("Выезд (ГГГГ-ММ-ДД)") },
                modifier = Modifier.weight(1f)
            )
        }

        // Количество гостей
        OutlinedTextField(
            value = adults,
            onValueChange = { newValue ->
                if (newValue.all { it.isDigit() }) {
                    adults = newValue.take(2)
                }
            },
            label = { Text("Количество гостей") },
            modifier = Modifier.fillMaxWidth()
        )

        // Фильтр по звёздам
        Text("Звёздность отеля", style = MaterialTheme.typography.labelMedium)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf(1, 2, 3, 4, 5).forEach { stars ->
                FilterChip(
                    selected = selectedStars.contains(stars),
                    onClick = {
                        selectedStars = if (selectedStars.contains(stars)) {
                            selectedStars - stars
                        } else {
                            selectedStars + stars
                        }
                    },
                    label = { Text("$stars★") }
                )
            }
        }

        // Фильтр по цене
        Text("Диапазон цен (₽)", style = MaterialTheme.typography.labelMedium)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = priceMin,
                onValueChange = { newValue ->
                    if (newValue.all { it.isDigit() }) {
                        priceMin = newValue.take(7)
                    }
                },
                label = { Text("От") },
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = priceMax,
                onValueChange = { newValue ->
                    if (newValue.all { it.isDigit() }) {
                        priceMax = newValue.take(7)
                    }
                },
                label = { Text("До") },
                modifier = Modifier.weight(1f)
            )
        }

        // Отображение ошибок
        error?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        // Кнопка поиска
        Button(
            onClick = {
                viewModel.searchHotels(
                    city = city,
                    checkIn = checkIn,
                    checkOut = checkOut,
                    adults = adults.toIntOrNull() ?: 2,
                    stars = selectedStars.takeIf { it.isNotEmpty() },
                    priceMin = priceMin.takeIf { it.isNotEmpty() }?.toIntOrNull(),
                    priceMax = priceMax.takeIf { it.isNotEmpty() }?.toIntOrNull()
                )
                navController.navigate("hotelResults")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Найти отели")
        }
    }
}
