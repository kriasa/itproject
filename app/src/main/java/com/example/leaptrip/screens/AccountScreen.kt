package com.example.leaptrip.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.leaptrip.R
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@Composable
fun AccountScreen(navController: NavController) {
    // Временные данные пользователя (замените на реальные из ViewModel)
    val userEmail = "user@example.com"
    val userName = "Иван Иванов"

    Scaffold(
        bottomBar = {
            BottomAppBar {
                // Ваше нижнее меню с 4 иконками
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("flightSearch") },
                    icon = { Icon(painterResource(R.drawable.telegram_icon), contentDescription = "Авиабилеты") },
                    label = { Text("Авиабилеты") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("hotels") },
                    icon = { Icon(painterResource(R.drawable.hotel_icon), contentDescription = "Отели") },
                    label = { Text("Отели") }
                )
                NavigationBarItem(
                    selected = true, // Активная иконка
                    onClick = { },
                    icon = { Icon(painterResource(R.drawable.account_icon), contentDescription = "Аккаунт") },
                    label = { Text("Аккаунт") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { /* Открытие Telegram */ },
                    icon = { Icon(painterResource(R.drawable.telegram_icon), contentDescription = "Чат") },
                    label = { Text("Чат") }
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Лягушка в шапке


            // Аватар и информация
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Аватар (можно заменить на реальное фото пользователя)
                Image(
                    painter = painterResource(R.drawable.ic_profile),
                    contentDescription = "Аватар",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = userName,
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = userEmail,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Кнопка выхода
                Button(
                    onClick = { /* Логика выхода */ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.ExitToApp, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Выйти из аккаунта")
                }
            }
        }
    }
}
