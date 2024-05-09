package xd.jg.custom_figures.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import xd.jg.custom_figures.presentation.auth_screen.AuthScreen
import xd.jg.custom_figures.presentation.basket_screen.BasketScreen
import xd.jg.custom_figures.presentation.catalog_screen.CatalogScreen
import xd.jg.custom_figures.presentation.custom_figure_detail_screen.CustomFigureDetailScreen
import xd.jg.custom_figures.presentation.figure_detail_screen.FigureDetailScreen
import xd.jg.custom_figures.presentation.model_from_photo_constructor_screen.ModelFromPhotoConstructorScreen
import xd.jg.custom_figures.presentation.model_from_text_constructor.ModelFromTextConstructorScreen
import xd.jg.custom_figures.presentation.profile_screen.ProfileScreen
import xd.jg.custom_figures.presentation.register_screen.RegisterScreen

@Composable
fun NavigationGraph(navController: NavHostController, onBottomVisibilityChanged: (Boolean) -> Unit) {
    NavHost(navController, startDestination = BottomNavigationItems.CatalogScreen.route) {
        composable(BottomNavigationItems.CatalogScreen.route) {
            onBottomVisibilityChanged(true)
            CatalogScreen(navController)
        }

        composable(BottomNavigationItems.ChatScreen.route) {
            navController.popBackStack()
            onBottomVisibilityChanged(true)
        }

        composable(BottomNavigationItems.AccountScreen.route) {
            onBottomVisibilityChanged(true)
            ProfileScreen(navController)
        }

        composable(BottomNavigationItems.BasketScreen.route) {
            onBottomVisibilityChanged(true)
            BasketScreen(navController)
        }

        composable(Routes.Auth.route) {
            onBottomVisibilityChanged(false)
            AuthScreen(navController)
        }

        composable(Routes.Register.route) {
            onBottomVisibilityChanged(false)
            RegisterScreen(navController)
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


        composable(Routes.ModelFromPhotoConstructorScreen.route) {
            onBottomVisibilityChanged(false)
            ModelFromPhotoConstructorScreen(navController)
        }

        composable(Routes.ModelFromTextConstructorScreen.route) {
            onBottomVisibilityChanged(false)
            ModelFromTextConstructorScreen(navController)
        }

        composable(
            Routes.CustomFigureDetailScreen.route,
            arguments = listOf(navArgument("figure_model_basket")
            {
                type = NavType.IntType
            })
        ) {backStackEntry ->
            val basketModelId = backStackEntry.arguments?.getInt("figure_model_basket") ?: return@composable
            onBottomVisibilityChanged(false)
            CustomFigureDetailScreen(navController, basketModelId)
        }
    }

}