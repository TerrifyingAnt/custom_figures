package xd.jg.custom_figures.presentation.navigation

sealed class Routes(val route: String) {
    object Auth : Routes("auth_screen")
    object Register : Routes("register_screen")

    object FigureDetailScreen : Routes("catalog_screen/{figure_model}")

    object ModelFromPhotoConstructorScreen : Routes("model_from_photo_constructor")

    object ModelFromTextConstructorScreen : Routes("model_from_text_constructor")
}