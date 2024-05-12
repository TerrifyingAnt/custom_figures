package xd.jg.custom_figures.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import xd.jg.custom_figures.presentation.auth_screen.AuthScreen
import xd.jg.custom_figures.presentation.basket_screen.BasketScreen
import xd.jg.custom_figures.presentation.catalog_screen.CatalogScreen
import xd.jg.custom_figures.presentation.custom_figure_detail_screen.CustomFigureDetailScreen
import xd.jg.custom_figures.presentation.edit_profile_screen.EditProfileScreen
import xd.jg.custom_figures.presentation.edit_profile_screen.EditProfileViewModel
import xd.jg.custom_figures.presentation.figure_detail_screen.FigureDetailScreen
import xd.jg.custom_figures.presentation.model_from_photo_constructor_screen.ModelFromPhotoConstructorScreen
import xd.jg.custom_figures.presentation.model_from_text_constructor.ModelFromTextConstructorScreen
import xd.jg.custom_figures.presentation.order_history_screen.OrderHistoryScreen
import xd.jg.custom_figures.presentation.profile_screen.ProfileScreen
import xd.jg.custom_figures.presentation.register_screen.RegisterScreen

@Composable
fun NavigationGraph(navController: NavHostController, onBottomVisibilityChanged: (Boolean) -> Unit, viewModel: EditProfileViewModel = hiltViewModel()) {
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
            onBottomVisibilityChanged(false)
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
        
        composable(
            Routes.EditProfileScreen.route,
            arguments = listOf(navArgument("user_email") {type = NavType.StringType}, navArgument("user_full_name") {type = NavType.StringType}, navArgument("user_phone"){type = NavType.StringType})
        ) {backStackEntry ->
            val userEmail = backStackEntry.arguments?.getString("user_email") ?: return@composable
            val userName = backStackEntry.arguments?.getString("user_full_name") ?: return@composable
            val userPhone = backStackEntry.arguments?.getString("user_phone") ?: return@composable
            onBottomVisibilityChanged(false)
            EditProfileScreen(navController = navController, userEmail = userEmail, userName = userName, userPhone = userPhone)
        }

        composable(Routes.OrderHistoryScreen.route) {
            onBottomVisibilityChanged(false)
            OrderHistoryScreen(navController)
        }
    }

}