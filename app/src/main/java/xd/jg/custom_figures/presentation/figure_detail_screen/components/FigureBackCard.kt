package xd.jg.custom_figures.presentation.figure_detail_screen.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import xd.jg.custom_figures.presentation.figure_detail_screen.FigureDetailViewModel
import xd.jg.custom_figures.ui.theme.CustomPrimary
import xd.jg.custom_figures.utils.Constants
import xd.jg.custom_figures.utils.Resource

@Composable
fun FigureBackCard(viewModel: FigureDetailViewModel = hiltViewModel()) {
    Card(colors = CardDefaults.cardColors(containerColor = CustomPrimary,),
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
            .border(1.dp, CustomPrimary, RoundedCornerShape(Constants.ROUNDED.dp)),
        shape = RoundedCornerShape(Constants.ROUNDED.dp),)
    {
        when (viewModel.figureDetailUIState.value.figureModel.status) {
            Resource.Status.SUCCESS -> {
                FigureCardViewer()
            }
            Resource.Status.LOADING -> {
                FigureCardViewerLoading()
            }
            Resource.Status.ERROR -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    Text("Что-то пошло не так, попробуйте позже: ${viewModel.figureDetailUIState.value.figureModel.message}")
                }
            }
        }
    }
}