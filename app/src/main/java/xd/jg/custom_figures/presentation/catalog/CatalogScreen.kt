package xd.jg.custom_figures.presentation.catalog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import xd.jg.custom_figures.R
import xd.jg.custom_figures.presentation.catalog.components.FigureList
import xd.jg.custom_figures.presentation.catalog.components.FilterList
import xd.jg.custom_figures.presentation.catalog.components.TypeOfConstructorDialog
import xd.jg.custom_figures.presentation.components.CustomButton
import xd.jg.custom_figures.presentation.components.CustomTextField
import xd.jg.custom_figures.ui.theme.CustomAccent
import xd.jg.custom_figures.ui.theme.CustomPrimaryContainer
import xd.jg.custom_figures.ui.theme.CustomPrimaryFixedDim
import xd.jg.custom_figures.ui.theme.CustomSecondary
import xd.jg.custom_figures.ui.theme.unboundedBoldFont

@Composable
fun CatalogScreen(
    navController: NavController,
    viewModel: CatalogViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.getFigures()
        viewModel.getTags()
    }

    Column(modifier = Modifier.fillMaxHeight(0.8f)) {
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Text(
                stringResource(R.string.catalog_string),
                fontFamily = unboundedBoldFont,
                fontSize = 30.sp,
                color = CustomSecondary
            )
        }
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            CustomTextField(
                description = stringResource(R.string.search_string),
                hint = "",
                textValue = viewModel.catalogUIState.value.currentFilterTitle.value,
                onValueChanged = viewModel::updateTitleFilter,
                modifier = Modifier.fillMaxWidth(0.8f),
                leadingIcon = Icons.Default.Search,
                backgroundColor = CustomPrimaryContainer,
                cursorColor = CustomAccent
            )
        }
        FilterList()
        FigureList(navController)
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier.fillMaxSize()
        ) {
            CustomButton(
                buttonColor = CustomPrimaryFixedDim,
                buttonText = stringResource(R.string.want_my_figure_string),
                onClick = { viewModel.updateIsDialogShown() },
                bold = true,
                modifiers = Modifier.fillMaxWidth(0.8f)
            )
        }

    when {
        viewModel.catalogUIState.value.isDialogShown.value -> {
            TypeOfConstructorDialog()
        }
    }
}