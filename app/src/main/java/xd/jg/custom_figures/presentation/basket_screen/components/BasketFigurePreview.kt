package xd.jg.custom_figures.presentation.basket_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import xd.jg.custom_figures.data.local.entity.BasketItemEntity
import xd.jg.custom_figures.presentation.basket_screen.BasketViewModel
import xd.jg.custom_figures.presentation.components.Counter
import xd.jg.custom_figures.presentation.navigation.Routes
import xd.jg.custom_figures.ui.theme.CustomPrimary
import xd.jg.custom_figures.ui.theme.unboundedRegularFont
import xd.jg.custom_figures.utils.Constants.ROUNDED

@Composable
fun BasketFigureCardPreview(
    navController: NavController,
    basketItemEntity: BasketItemEntity,
    viewModel: BasketViewModel = hiltViewModel()) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = CustomPrimary,
        ),
        modifier = Modifier
            .wrapContentSize()
            .border(1.dp, CustomPrimary, RoundedCornerShape(ROUNDED.dp))
            .clickable {
                navController.navigate(
                    Routes.CustomFigureDetailScreen.route.replace(
                        "{figure_model_basket}",
                        basketItemEntity.id.toString()
                    )
                )
            },
        shape = RoundedCornerShape(ROUNDED.dp),
    ) {
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Text(text = basketItemEntity.title,
                fontFamily = unboundedRegularFont,
                color = Color.White,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }
        Row(horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(ROUNDED))
                .background(Color.White)
        ) {
            Counter(basketItemEntity.count, addButton = { viewModel.addFigureCount(basketItemEntity.id) }, subtractButton = { viewModel.subtractFigureCount(basketItemEntity.id) }, inverse = true)
        }
    }
}