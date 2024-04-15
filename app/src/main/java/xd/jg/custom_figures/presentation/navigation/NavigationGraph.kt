package xd.jg.custom_figures.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import xd.jg.custom_figures.presentation.auth_screen.AuthScreen
import xd.jg.custom_figures.presentation.catalog.CatalogScreen
import xd.jg.custom_figures.presentation.register_screen.RegisterScreen

@Composable
fun NavigationGraph(navController: NavHostController, onBottomVisibilityChanged: (Boolean) -> Unit) {
    NavHost(navController, startDestination = Routes.Auth.route) {
        composable(BottomNavigationItems.CatalogScreen.route) {
            onBottomVisibilityChanged(true)
            CatalogScreen()
        }

        composable(BottomNavigationItems.ChatScreen.route) {
            onBottomVisibilityChanged(true)
        }

        composable(BottomNavigationItems.AccountScreen.route) {
            onBottomVisibilityChanged(true)

        }

        composable(BottomNavigationItems.BasketScreen.route) {
            onBottomVisibilityChanged(true)
        }

        composable(Routes.Auth.route) {
            onBottomVisibilityChanged(false)
            AuthScreen(navController)
        }

        composable(Routes.Register.route) {
            onBottomVisibilityChanged(false)
            RegisterScreen(navController)
        }
    }

}