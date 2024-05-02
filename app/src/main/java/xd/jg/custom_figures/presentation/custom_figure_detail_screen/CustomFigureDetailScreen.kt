package xd.jg.custom_figures.presentation.custom_figure_detail_screen

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import xd.jg.custom_figures.data.local.entity.FigureType
import xd.jg.custom_figures.presentation.custom_figure_detail_screen.components.TextFigureDetails

@Composable
fun CustomFigureDetailScreen(
    navController: NavController,
    basketModelId: Int,
    viewModel: CustomFigureDetailViewModel = hiltViewModel()) {
    LaunchedEffect(Unit) {
        viewModel.getFigureDetails(basketModelId)
    }

    when {
        viewModel.figureCustomDetailUIState.value.currentFigure.value != null -> {
            val figure = viewModel.figureCustomDetailUIState.value.currentFigure.value
            if (figure?.type == FigureType.CUSTOM_BY_TEXT.ordinal) {
                TextFigureDetails(navController, figure)
            }
            if (figure?.type == FigureType.CUSTOM_BY_PHOTO.ordinal) {
                //PhotoFigureDetails(navController, figure)
                /** TODO - жесть, потом переделать*/
                Toast.makeText(LocalContext.current, "Превью не загрузилос", Toast.LENGTH_SHORT).show()
            }
        }
    }
}