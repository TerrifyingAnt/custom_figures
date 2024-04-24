package xd.jg.custom_figures.presentation.figure_detail_screen.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.tbuonomo.viewpagerdotsindicator.compose.DotsIndicator
import com.tbuonomo.viewpagerdotsindicator.compose.model.DotGraphic
import com.tbuonomo.viewpagerdotsindicator.compose.type.WormIndicatorType
import xd.jg.custom_figures.R
import xd.jg.custom_figures.data.dto.FigureDto
import xd.jg.custom_figures.data.dto.TagDto
import xd.jg.custom_figures.presentation.catalog_screen.components.CustomTag
import xd.jg.custom_figures.presentation.components.Counter
import xd.jg.custom_figures.presentation.components.CustomButton
import xd.jg.custom_figures.presentation.figure_detail_screen.FigureDetailViewModel
import xd.jg.custom_figures.ui.theme.CustomPrimary
import xd.jg.custom_figures.ui.theme.CustomPrimaryContainer
import xd.jg.custom_figures.ui.theme.CustomPrimaryFixedDim
import xd.jg.custom_figures.ui.theme.unboundedBoldFont
import xd.jg.custom_figures.utils.Constants
import xd.jg.custom_figures.utils.toTagFilter

@OptIn(ExperimentalFoundationApi::class, ExperimentalCoilApi::class)
@Composable
fun FigureFrontCard(figure: FigureDto, viewModel: FigureDetailViewModel = hiltViewModel()) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
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
                        contentScale = ContentScale.Fit
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
            LazyColumn {
                item {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = figure.title,
                            fontFamily = unboundedBoldFont,
                            color = White,
                            fontSize = 24.sp
                        )
                    }
                }
                item {
                    Divider()
                }
                item {
                    Text(text = stringResource(R.string.description_string), fontFamily = unboundedBoldFont, color = White, fontSize = 24.sp)
                }
                item {
                    Text(text = figure.description, fontFamily = unboundedBoldFont, color = White, fontSize = 18.sp)
                }
                item {
                    Divider()
                }
                item {
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
                }
                item {
                    when {
                        viewModel.figureDetailUIState.value.count.value == 0 -> {
                            Column(
                                verticalArrangement = Arrangement.Bottom, modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(5.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.Bottom,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    CustomButton(
                                        buttonColor = CustomPrimaryFixedDim,
                                        buttonText = "Купить",
                                        onClick = { viewModel.insertFigureToBasket(figure) },
                                        modifiers = Modifier.fillMaxWidth(0.6f)
                                    )
                                }
                            }
                        }
                        else -> {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                                Counter(viewModel.figureDetailUIState.value.count.value,
                                    { viewModel.addFigureCount(figure.id) },
                                    { viewModel.subtractFigureCount(figure.id) },
                                    24)
                            }
                        }

                    }
                }
            }


        }
    }
}


@Composable
@Preview
fun FigureCardPreview() {
    FigureFrontCard(
        FigureDto(
            1, "xd", "Фигурка персонажа Ято из аниме бездомный Бог. Материалы: пластик, размеры похуйХпохуйХпохуй", 1234f,
            3f, false, "sdf",
            listOf(""), "sdfwf", mutableListOf(TagDto(1, "АХАХА")
            )
        )
    )
}