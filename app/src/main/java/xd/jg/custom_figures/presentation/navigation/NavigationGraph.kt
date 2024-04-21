package xd.jg.custom_figures.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import xd.jg.custom_figures.presentation.auth_screen.AuthScreen
import xd.jg.custom_figures.presentation.catalog_screen.CatalogScreen
import xd.jg.custom_figures.presentation.figure_detail_screen.FigureDetailScreen
import xd.jg.custom_figures.presentation.model_from_photo_constructor_screen.ModelFromPhotoConstructorScreen
import xd.jg.custom_figures.presentation.register_screen.RegisterScreen

@Composable
fun NavigationGraph(navController: NavHostController, onBottomVisibilityChanged: (Boolean) -> Unit) {
    NavHost(navController, startDestination = Routes.Auth.route) {
        composable(BottomNavigationItems.CatalogScreen.route) {
            onBottomVisibilityChanged(true)
            CatalogScreen(navController)
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

        composable(
            route = Routes.FigureDetailScreen.route,
            arguments = listOf(navArgument("figure_model") {
            type = NavType.IntType
            })
        ) {backStackEntry ->
            val figureModelId = backStackEntry.arguments?.getInt("figure_model")
            onBottomVisibilityChanged(true)
            if (figureModelId != null) {
                FigureDetailScreen(navController, figureModelId)
            }
        }

        composable(Routes.Register.route) {
            onBottomVisibilityChanged(true)
            RegisterScreen(navController)
        }

        composable(Routes.ModelFromPhotoConstructorScreen.route) {
            onBottomVisibilityChanged(false)
            ModelFromPhotoConstructorScreen(navController)
        }
    }

}