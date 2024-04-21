package xd.jg.custom_figures.presentation.figure_detail_screen

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardBackspace
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.wajahatkarim.flippable.Flippable
import com.wajahatkarim.flippable.rememberFlipController
import xd.jg.custom_figures.R
import xd.jg.custom_figures.presentation.figure_detail_screen.components.FigureBackCard
import xd.jg.custom_figures.presentation.figure_detail_screen.components.FigureCardLoading
import xd.jg.custom_figures.presentation.figure_detail_screen.components.FigureFrontCard
import xd.jg.custom_figures.presentation.navigation.BottomNavigationItems
import xd.jg.custom_figures.ui.theme.CustomSecondary
import xd.jg.custom_figures.ui.theme.unboundedBoldFont
import xd.jg.custom_figures.utils.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FigureDetailScreen(
    navController: NavController,
    figureModelId: Int,
    viewModel: FigureDetailViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.updateClearSceneState(false)
        viewModel.getFigureInfo(figureModelId, context)
    }
    when {
        viewModel.figureDetailUIState.value.clearScene.value && viewModel.figureDetailUIState.value.figureModel.status == Resource.Status.LOADING -> {
            navController.popBackStack(BottomNavigationItems.CatalogScreen.route, false)
        }
    }

    BackHandler {
        viewModel.updateClearSceneState(true)
    }

    Column(modifier = Modifier.fillMaxSize()){
        TopAppBar(
            title = { Text(stringResource(R.string.figure_string), fontFamily = unboundedBoldFont, color = CustomSecondary) },
            navigationIcon = {
                IconButton(onClick = {
                    viewModel.updateClearSceneState(true)
                }) {
                    Icon(
                        Icons.AutoMirrored.Filled.KeyboardBackspace,
                        contentDescription = "go back",
                        tint = CustomSecondary,
                        modifier = Modifier
                            .size(50.dp)
                    )
                }
            }
        )

        when (viewModel.figureDetailUIState.value.figureDetails.status) {
            Resource.Status.LOADING -> {
                FigureCardLoading()
            }
            Resource.Status.SUCCESS -> {
                val figure = viewModel.figureDetailUIState.value.figureDetails.data ?: return
                Flippable(frontSide = { FigureFrontCard(figure) },
                    backSide = { FigureBackCard() },
                    flipController = rememberFlipController())

            }
            Resource.Status.ERROR -> {
                Toast.makeText(LocalContext.current, "${viewModel.figureDetailUIState.value.figureDetails.message}", Toast.LENGTH_SHORT ).show()
            }
        }
    }

}