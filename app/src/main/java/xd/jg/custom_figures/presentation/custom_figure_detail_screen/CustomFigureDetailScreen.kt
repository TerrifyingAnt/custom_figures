package xd.jg.custom_figures.presentation.custom_figure_detail_screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun CustomFigureDetailScreen(navController: NavController, basketModelId: Int) {
    Text(basketModelId.toString())
}