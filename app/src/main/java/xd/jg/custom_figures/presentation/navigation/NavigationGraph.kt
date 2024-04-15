package xd.jg.custom_figures.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import xd.jg.custom_figures.presentation.catalog.CatalogScreen

@Composable
fun NavigationGraph(navController: NavHostController, onBottomVisibilityChanged: (Boolean) -> Unit) {
    NavHost(navController, startDestination = BottomNavigationItems.CatalogScreen.route) {
        composable(BottomNavigationItems.CatalogScreen.route) {
            onBottomVisibilityChanged(false)
            CatalogScreen()
        }

        composable(BottomNavigationItems.ChatScreen.route) {
            onBottomVisibilityChanged(false)
            CatalogScreen()
        }

        composable(BottomNavigationItems.AccountScreen.route) {
            onBottomVisibilityChanged(false)

        }

        composable(BottomNavigationItems.BasketScreen.route) {
            onBottomVisibilityChanged(false)

        }
    }

}