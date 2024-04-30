package xd.jg.custom_figures.presentation.basket_screen.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import xd.jg.custom_figures.data.dto.FigurePreviewDto
import xd.jg.custom_figures.presentation.components.Counter
import xd.jg.custom_figures.presentation.components.FigurePreviewCard
import xd.jg.custom_figures.ui.theme.CustomPrimary
import xd.jg.custom_figures.utils.Constants

@Composable
fun BasketFigureCatalogPreview(navController: NavController, figurePreviewDto: FigurePreviewDto) {
    Column(modifier = Modifier
        .wrapContentSize()
        .border(
            1.dp,
            CustomPrimary,
            RoundedCornerShape(Constants.ROUNDED.dp)
        )) {
        FigurePreviewCard(navController, figurePreviewDto)
        Row(modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(Constants.ROUNDED)
            ), horizontalArrangement = Arrangement.Center) {
            Counter(1, inverse = true)
        }
    }
}