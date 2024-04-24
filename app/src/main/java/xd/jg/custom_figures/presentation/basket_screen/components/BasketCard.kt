package xd.jg.custom_figures.presentation.basket_screen.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.tbuonomo.viewpagerdotsindicator.compose.DotsIndicator
import com.tbuonomo.viewpagerdotsindicator.compose.model.DotGraphic
import com.tbuonomo.viewpagerdotsindicator.compose.type.WormIndicatorType
import xd.jg.custom_figures.data.dto.FigureDto
import xd.jg.custom_figures.presentation.components.Counter
import xd.jg.custom_figures.presentation.navigation.Routes
import xd.jg.custom_figures.ui.theme.CustomPrimary
import xd.jg.custom_figures.ui.theme.CustomPrimaryContainer
import xd.jg.custom_figures.utils.Constants

@OptIn(ExperimentalFoundationApi::class, ExperimentalCoilApi::class)
@Composable
fun BasketCard(navController: NavController, figure: FigureDto) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = CustomPrimary,
        ),
        modifier = Modifier
            .height(230.dp)
            .width(150.dp)
            .border(1.dp, CustomPrimary, RoundedCornerShape(Constants.ROUNDED.dp))
            .clickable {
                navController.navigate(
                    Routes.FigureDetailScreen.route.replace(
                        "{figure_model}",
                        figure.id.toString()
                    )
                )
            },
        shape = RoundedCornerShape(Constants.ROUNDED.dp),
    ) {
        val state = rememberPagerState(pageCount = { figure.imageLinks.size })
        val imageUrl = remember { mutableStateOf("") }
        Surface(modifier = Modifier
            .height(150.dp)
            .fillMaxWidth()
            .border(0.dp, CustomPrimary, RoundedCornerShape(Constants.ROUNDED.dp)),
            shape = RoundedCornerShape(Constants.ROUNDED.dp)
        ) {
            HorizontalPager(
                state = state
            ) { page ->
                imageUrl.value = figure.imageLinks[page]
                Image(
                    painter = rememberImagePainter(imageUrl.value),
                    contentDescription = "test_image",
                )
            }
            Column(verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.CenterHorizontally) {
                Box(contentAlignment = Alignment.Center) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth(0.1f * figure.imageLinks.size + 0.05f)
                            .clip(
                                RoundedCornerShape(
                                    Constants.ROUNDED.dp
                                )
                            )
                            .background(CustomPrimary)
                    ) {
                        DotsIndicator(
                            dotCount = figure.imageLinks.size,
                            dotSpacing = 5.dp,
                            type = WormIndicatorType(
                                dotsGraphic = DotGraphic(
                                    8.dp,
                                    color = CustomPrimaryContainer
                                ),
                                wormDotGraphic = DotGraphic(
                                    8.dp,
                                    color = Color.White,
                                ),
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 2.dp, 0.dp, 2.dp)
                                .background(CustomPrimary),
                            pagerState = state
                        )
                    }
                }
                Spacer(Modifier.size(2.dp))
            }
        }
        Counter(1)
    }
}

