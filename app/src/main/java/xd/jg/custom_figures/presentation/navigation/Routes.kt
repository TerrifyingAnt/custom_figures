package xd.jg.custom_figures.presentation.navigation

sealed class Routes(val route: String) {
    object Start : Routes("start_screen")
}