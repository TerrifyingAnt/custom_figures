package xd.jg.custom_figures.presentation.FigureDetailScreen

import android.widget.Toast
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import xd.jg.custom_figures.presentation.FigureDetailScreen.components.FigureCard
import xd.jg.custom_figures.utils.Resource

@Composable
fun FigureDetailScreen(
    navController: NavController,
    figureModelId: Int,
    viewModel: FigureDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.getFigureInfo(figureModelId)
    }

    when (viewModel.figureDetailUIState.value.figureDetails.status) {
        Resource.Status.LOADING -> {
            CircularProgressIndicator()
        }
        Resource.Status.SUCCESS -> {
            val figure = viewModel.figureDetailUIState.value.figureDetails.data ?: return
            FigureCard(figure, navController)
        }
        Resource.Status.ERROR -> {
            Toast.makeText(LocalContext.current, "${viewModel.figureDetailUIState.value.figureDetails.message}", Toast.LENGTH_SHORT ).show()
        }
    }

}