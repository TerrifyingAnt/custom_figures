package xd.jg.custom_figures.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavigationItems(
    val route: String,
    val title: String? = null,
    val icon: ImageVector? = null
) {
    object CatalogScreen : BottomNavigationItems (
        route = "catalog_screen",
        title = "catalog_screen",
        icon = Icons.Default.Home
    )

    object BasketScreen : BottomNavigationItems (
        route = "basket_screen",
        title = "basket_screen",
        icon = Icons.Default.ShoppingCart
    )

    object ChatScreen : BottomNavigationItems (
        route = "chat_screen",
        title = "chat_screen",
        icon = Icons.Default.ChatBubble
    )

    object AccountScreen : BottomNavigationItems (
        route = "account_screen",
        title = "account_screen",
        icon = Icons.Default.AccountCircle
    )
}