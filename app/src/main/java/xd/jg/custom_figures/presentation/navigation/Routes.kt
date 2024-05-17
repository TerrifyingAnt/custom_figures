package xd.jg.custom_figures.presentation.navigation

sealed class Routes(val route: String) {
    object Auth : Routes("auth_screen")
    object Register : Routes("register_screen")

    object FigureDetailScreen : Routes("catalog_screen/{figure_model}")

    object ModelFromPhotoConstructorScreen : Routes("model_from_photo_constructor")

    object ModelFromTextConstructorScreen : Routes("model_from_text_constructor")

    object CustomFigureDetailScreen : Routes("catalog_screen_custom/{figure_model_basket}")

    object EditProfileScreen :
        Routes("edit_profile_screen?{user_email}&{user_phone}&{user_full_name}")

    object OrderHistoryScreen : Routes("order_history_screen")

    object ChatScreen : Routes("dialogs_screen/{chat_id}")
}