package xd.jg.custom_figures.presentation.navigation

sealed class Routes(val route: String) {
    object Auth : Routes("auth_screen")
    object Register : Routes("register_screen")
}