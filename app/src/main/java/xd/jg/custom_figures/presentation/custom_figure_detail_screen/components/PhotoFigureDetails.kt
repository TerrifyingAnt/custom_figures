package xd.jg.custom_figures.presentation.custom_figure_detail_screen.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardBackspace
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import xd.jg.custom_figures.R
import xd.jg.custom_figures.data.local.entity.BasketItemEntity
import xd.jg.custom_figures.presentation.custom_figure_detail_screen.CustomFigureDetailViewModel
import xd.jg.custom_figures.presentation.figure_detail_screen.components.FigureCardViewer
import xd.jg.custom_figures.presentation.figure_detail_screen.components.FigureCardViewerLoading
import xd.jg.custom_figures.presentation.navigation.BottomNavigationItems
import xd.jg.custom_figures.ui.theme.CustomPrimary
import xd.jg.custom_figures.ui.theme.CustomSecondary
import xd.jg.custom_figures.ui.theme.unboundedBoldFont
import xd.jg.custom_figures.utils.Constants
import xd.jg.custom_figures.utils.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoFigureDetails(
    navController: NavController,
    figure: BasketItemEntity,
    viewModel: CustomFigureDetailViewModel = hiltViewModel()) {
    Column(modifier = Modifier.fillMaxHeight()) {
        TopAppBar(
            title = {
                Text(
                    stringResource(R.string.figure_string),
                    fontFamily = unboundedBoldFont,
                    color = CustomSecondary
                )
            },
            navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack(BottomNavigationItems.BasketScreen.route, false)
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

        Card(colors = CardDefaults.cardColors(containerColor = CustomPrimary,),
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
                .border(1.dp, CustomPrimary, RoundedCornerShape(Constants.ROUNDED.dp)),
            shape = RoundedCornerShape(Constants.ROUNDED.dp),)
        {
            when {
                viewModel.figureCustomDetailUIState.value.figureModel.status == Resource.Status.SUCCESS -> {
                    FigureCardViewer()
                }
                viewModel.figureCustomDetailUIState.value.figureModel.status == Resource.Status.LOADING -> {
                    FigureCardViewerLoading()
                }

            }
        }

    }
}