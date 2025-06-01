package com.example.leaptrip.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import com.example.leaptrip.viewmodel.FlightViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FlightSearchScreen(
    navController: NavHostController,
    flightViewModel: FlightViewModel
) {
    // Состояния для полей ввода
    var fromCity by remember { mutableStateOf("") }
    var toCity by remember { mutableStateOf("") }
    var departureDate by remember { mutableStateOf("") }
    var returnDate by remember { mutableStateOf("") }
    var oneWay by remember { mutableStateOf(false) }
    var direct by remember { mutableStateOf(false) }

    val isLoading by flightViewModel.isLoading.collectAsState()
    val error by flightViewModel.error.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        OutlinedTextField(
            value = fromCity,
            onValueChange = { fromCity = it },
            label = { Text("Откуда") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = toCity,
            onValueChange = { toCity = it },
            label = { Text("Куда") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = departureDate,
            onValueChange = { departureDate = it },
            label = { Text("Дата вылета (YYYY-MM-DD)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = returnDate,
            onValueChange = { returnDate = it },
            label = { Text("Дата возвращения (опционально)") },
            modifier = Modifier.fillMaxWidth(),
            enabled = !oneWay
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = oneWay, onCheckedChange = { oneWay = it })
            Spacer(modifier = Modifier.width(8.dp))
            Text("В один конец")
        }
        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = direct, onCheckedChange = { direct = it })
            Spacer(modifier = Modifier.width(8.dp))
            Text("Прямые рейсы")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                flightViewModel.searchFlights(
                    fromCity = fromCity,
                    toCity = toCity,
                    departureDate = departureDate,
                    returnDate = if (returnDate.isBlank()) null else returnDate,
                    oneWay = oneWay,
                    direct = direct
                )
                navController.navigate("results")
            },
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Поиск")
        }

        if (isLoading) {
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        if (!error.isNullOrEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = error ?: "",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
