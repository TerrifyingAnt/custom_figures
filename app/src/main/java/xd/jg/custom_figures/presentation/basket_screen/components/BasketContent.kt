package xd.jg.custom_figures.presentation.basket_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import xd.jg.custom_figures.data.local.entity.FigureType
import xd.jg.custom_figures.presentation.basket_screen.BasketViewModel
import xd.jg.custom_figures.presentation.figure_detail_screen.components.FigureCardLoading
import xd.jg.custom_figures.ui.theme.CustomPrimary
import xd.jg.custom_figures.ui.theme.unboundedBoldFont
import xd.jg.custom_figures.utils.Resource

@Composable
fun BasketContent(navController: NavController, viewModel: BasketViewModel = hiltViewModel()) {
    if (viewModel.basketUIState.value.basketFigures.value?.isEmpty() == true) {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text("Корзина пока пуста!", fontFamily = unboundedBoldFont, color = CustomPrimary, textAlign = TextAlign.Center, fontSize = 20.sp)
            }
        }
    }
    else {
        LazyVerticalGrid(columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(25.dp),
            verticalArrangement = Arrangement.spacedBy(25.dp),
            contentPadding =  PaddingValues(30.dp, 10.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(viewModel.basketUIState.value.basketFigures.value?.size ?: 0) {
                val figures = viewModel.basketUIState.value.basketFigures.value ?: listOf()
                if (figures[it].type  == FigureType.CUSTOM_DEFAULT.ordinal) {
                    when {
                        viewModel.basketUIState.value.previewDefaultFigures.status == Resource.Status.SUCCESS -> {
                            val figurePreviewDto =
                                viewModel.basketUIState.value.previewDefaultFigures.data?.find { figurePreview -> figurePreview.id == figures[it].figureId }
                            if (figurePreviewDto != null) {
                                BasketFigureCatalogPreview(navController, figurePreviewDto)
                            }
                        }
                        viewModel.basketUIState.value.previewDefaultFigures.status == Resource.Status.LOADING -> {
                            FigureCardLoading()
                        }
                    }
                }
                else {
                    BasketFigureCardPreview(navController, figures[it])
                }
            }
        }
    }
}