package com.example.leaptrip.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.leaptrip.viewmodel.AccountViewModel
import com.example.leaptrip.viewmodel.AccountViewModelFactory
import com.example.leaptrip.network.UserApiService

@Composable
fun AccountScreen(
    navController: NavHostController,
    userApiService: UserApiService,
) {
    val factory = AccountViewModelFactory(userApiService)
    val accountViewModel: AccountViewModel = viewModel(factory = factory)

    val user by accountViewModel.user.collectAsState()
    val isLoading by accountViewModel.isLoading.collectAsState()
    val error by accountViewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        accountViewModel.loadUser()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Личный кабинет", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(24.dp))

        if (isLoading) {
            CircularProgressIndicator()
        } else if (error != null) {
            Text(text = error ?: "", color = MaterialTheme.colorScheme.error)
        } else if (user != null) {
            Text(text = "Имя пользователя: ${user!!.username}")
            Text(text = "Email: ${user!!.email}")
            Text(text = "Активен: ${if (user!!.is_active) "Да" else "Нет"}")

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = {
                accountViewModel.logout()
                navController.navigate("login") { popUpTo(0) }
            }) {
                Text("Выйти")
            }
        }
    }
}
