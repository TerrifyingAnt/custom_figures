package xd.jg.custom_figures.presentation.catalog_screen.components

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import xd.jg.custom_figures.presentation.catalog_screen.CatalogViewModel
import xd.jg.custom_figures.utils.Constants
import xd.jg.custom_figures.utils.Resource

@Composable
fun FilterList(
    viewModel: CatalogViewModel = hiltViewModel()
) {
    when(viewModel.catalogUIState.value.currentFiltersTag.status) {
        Resource.Status.LOADING -> {
            TagsShimmerLoading()
        }
        Resource.Status.ERROR -> {
            Toast.makeText(LocalContext.current, viewModel.catalogUIState.value.currentFiltersTag.message, Toast.LENGTH_SHORT).show()
        }
        Resource.Status.SUCCESS -> {
            val tags = viewModel.catalogUIState.value.currentFiltersTag.data ?: listOf()
            LazyRow(modifier = Modifier
                .fillMaxWidth()
                .padding(Constants.START_PADDING.dp, 0.dp, Constants.END_PADDING.dp, 0.dp)) {
                items(tags.size) {tag ->
                    CustomTag(tag = tags[tag], onClick = { viewModel.updateIsPressedFilter(tags[tag]) }, modifier = Modifier
                        .wrapContentSize()
                        .padding(5.dp, 5.dp, 5.dp, 5.dp), spacersSize = 10)
                }
            }
        }
    }
}