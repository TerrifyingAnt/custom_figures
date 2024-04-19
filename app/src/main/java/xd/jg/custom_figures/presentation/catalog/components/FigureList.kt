package xd.jg.custom_figures.presentation.catalog.components

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import xd.jg.custom_figures.presentation.catalog.CatalogViewModel
import xd.jg.custom_figures.utils.Resource

@Composable
fun FigureList(
    navController: NavController,
    viewModel: CatalogViewModel = hiltViewModel()
) {
    when(viewModel.catalogUIState.value.currentUIState.status) {
        Resource.Status.LOADING ->  {
            FiguresShimmerLoading()
        }
        Resource.Status.ERROR -> {
            Toast.makeText(LocalContext.current, viewModel.catalogUIState.value.currentFiltersTag.message, Toast.LENGTH_SHORT).show()
        }
        Resource.Status.SUCCESS -> {
            val figure = viewModel.catalogUIState.value.currentUIState.data ?: listOf()
            LazyVerticalGrid(columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(25.dp),
                verticalArrangement = Arrangement.spacedBy(25.dp),
                contentPadding =  PaddingValues(30.dp, 10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(figure.size) {figureId ->
                    FigurePreviewCard(navController, figure[figureId])
                }
            }
        }
    }

}