package xd.jg.custom_figures.presentation.chat_screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun ChatScreen(navController: NavController, chatId: Int) {
    Text(chatId.toString())
}