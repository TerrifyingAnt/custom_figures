package xd.jg.custom_figures.presentation.FigureDetailScreen.components

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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardBackspace
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.tbuonomo.viewpagerdotsindicator.compose.DotsIndicator
import com.tbuonomo.viewpagerdotsindicator.compose.model.DotGraphic
import com.tbuonomo.viewpagerdotsindicator.compose.type.WormIndicatorType
import xd.jg.custom_figures.R
import xd.jg.custom_figures.data.dto.FigureDto
import xd.jg.custom_figures.data.dto.TagDto
import xd.jg.custom_figures.presentation.catalog.components.CustomTag
import xd.jg.custom_figures.presentation.components.CustomButton
import xd.jg.custom_figures.presentation.navigation.BottomNavigationItems
import xd.jg.custom_figures.ui.theme.CustomPrimary
import xd.jg.custom_figures.ui.theme.CustomPrimaryContainer
import xd.jg.custom_figures.ui.theme.CustomPrimaryFixedDim
import xd.jg.custom_figures.ui.theme.CustomSecondary
import xd.jg.custom_figures.ui.theme.unboundedBoldFont
import xd.jg.custom_figures.utils.Constants
import xd.jg.custom_figures.utils.toTagFilter

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun FigureCard(figure: FigureDto, navController: NavController?) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Фигурка", fontFamily = unboundedBoldFont, color = CustomSecondary) },
            navigationIcon = {
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardBackspace,
                    contentDescription = "go fucking back",
                    tint = CustomSecondary,
                    modifier = Modifier
                        .size(50.dp)
                        .clickable {
                            navController?.popBackStack(
                                BottomNavigationItems.CatalogScreen.route,
                                false
                            )
                        }
                )
            }
        )
        Card(
            colors = CardDefaults.cardColors(
                containerColor = CustomPrimary,
            ),
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
                .border(1.dp, CustomPrimary, RoundedCornerShape(Constants.ROUNDED.dp)),
            shape = RoundedCornerShape(Constants.ROUNDED.dp),
        ) {
            val state = rememberPagerState(pageCount = { figure.imageLinks.size })
            val imageUrl = remember { mutableStateOf("") }
            Surface(
                modifier = Modifier
                    .fillMaxHeight(0.6f)
                    .fillMaxWidth()
                    .border(1.dp, CustomPrimary, RoundedCornerShape(Constants.ROUNDED.dp)),
                shape = RoundedCornerShape(Constants.ROUNDED.dp)
            ) {
                HorizontalPager(
                    state = state
                ) { page ->
                    imageUrl.value = figure.imageLinks[page]
                    Image(
                        painter = rememberImagePainter(imageUrl.value),
                        contentDescription = "test_image",
                        contentScale = ContentScale.FillBounds
                    )
                }
                Column(
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
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
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                Text(text = figure.title, fontFamily = unboundedBoldFont, color = White, fontSize = 24.sp)
            }
            Divider()
            Text(text = stringResource(R.string.description_string), fontFamily = unboundedBoldFont, color = White, fontSize = 24.sp)
            Text(text = figure.description, fontFamily = unboundedBoldFont, color = White, fontSize = 18.sp)
            Divider()
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = stringResource(R.string.tags_string), fontFamily = unboundedBoldFont, color = White, fontSize = 20.sp)
                LazyRow(modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .padding(5.dp, 5.dp, 5.dp, 2.dp)) {
                    items(figure.tags.size) { tag ->
                        CustomTag(
                            tag = figure.tags[tag].toTagFilter(),
                            modifier = Modifier.wrapContentWidth()
                        )
                        Spacer(modifier = Modifier.size(5.dp))
                    }
                }
                Text(text = "Цена: ${figure.price}", fontFamily = unboundedBoldFont, color = White, fontSize = 20.sp)
            }
            Column(verticalArrangement = Arrangement.Bottom, modifier = Modifier.fillMaxHeight().padding(5.dp)) {
                Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                    CustomButton(
                        buttonColor = CustomPrimaryFixedDim,
                        buttonText = "Купить",
                        onClick = { /*TODO*/ },
                        modifiers = Modifier.fillMaxWidth(0.6f)
                    )
                }
            }
        }
    }
}


@Composable
@Preview
fun FigureCardPreview() {
    FigureCard(
        FigureDto(
            1, "xd", "Фигурка персонажа Ято из аниме бездомный Бог. Материалы: пластик, размеры похуйХпохуйХпохуй", 1234f,
            3f, false, "sdf",
            listOf(""), "sdfwf", mutableListOf(TagDto(1, "АХАХА")
            )
        ),
        null
    )
}