package xd.jg.custom_figures.presentation.catalog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import xd.jg.custom_figures.R
import xd.jg.custom_figures.data.dto.FigurePreviewDto
import xd.jg.custom_figures.data.dto.TagDto
import xd.jg.custom_figures.presentation.catalog.components.CustomTag
import xd.jg.custom_figures.presentation.catalog.components.FigureCard
import xd.jg.custom_figures.presentation.components.CustomTextField
import xd.jg.custom_figures.ui.theme.CustomAccent
import xd.jg.custom_figures.ui.theme.CustomPrimaryContainer
import xd.jg.custom_figures.ui.theme.CustomSecondary
import xd.jg.custom_figures.ui.theme.unboundedBoldFont
import xd.jg.custom_figures.utils.Constants.END_PADDING
import xd.jg.custom_figures.utils.Constants.START_PADDING

@Composable
fun CatalogScreen() {
    val figure = listOf(
        FigurePreviewDto(1, "xd", 100f, listOf("https://64.media.tumblr.com/62ee8b8ed1b8f8c66468e79d385ee6a0/tumblr_p18opnjl8D1rm7kqjo2_1280.jpg", "https://yt3.googleusercontent.com/ytc/AGIKgqPItmnFwqdWPusJ2ZpLn0NX5tYtBfmp5xFTQFkq=s900-c-k-c0x00ffffff-no-rj"), mutableListOf(
            TagDto(1, "онеме"), TagDto(2, "some cringe")
        )),
        FigurePreviewDto(1, "xd", 100f, listOf("https://64.media.tumblr.com/62ee8b8ed1b8f8c66468e79d385ee6a0/tumblr_p18opnjl8D1rm7kqjo2_1280.jpg", "https://yt3.googleusercontent.com/ytc/AGIKgqPItmnFwqdWPusJ2ZpLn0NX5tYtBfmp5xFTQFkq=s900-c-k-c0x00ffffff-no-rj", "https://yt3.googleusercontent.com/ytc/AGIKgqPItmnFwqdWPusJ2ZpLn0NX5tYtBfmp5xFTQFkq=s900-c-k-c0x00ffffff-no-rj"), mutableListOf(
            TagDto(1, "онеме"), TagDto(2, "some cringe")
        )),
        FigurePreviewDto(1, "xd", 100f, listOf("https://64.media.tumblr.com/62ee8b8ed1b8f8c66468e79d385ee6a0/tumblr_p18opnjl8D1rm7kqjo2_1280.jpg", "https://yt3.googleusercontent.com/ytc/AGIKgqPItmnFwqdWPusJ2ZpLn0NX5tYtBfmp5xFTQFkq=s900-c-k-c0x00ffffff-no-rj", "https://64.media.tumblr.com/62ee8b8ed1b8f8c66468e79d385ee6a0/tumblr_p18opnjl8D1rm7kqjo2_1280.jpg", "https://yt3.googleusercontent.com/ytc/AGIKgqPItmnFwqdWPusJ2ZpLn0NX5tYtBfmp5xFTQFkq=s900-c-k-c0x00ffffff-no-rj", "https://yt3.googleusercontent.com/ytc/AGIKgqPItmnFwqdWPusJ2ZpLn0NX5tYtBfmp5xFTQFkq=s900-c-k-c0x00ffffff-no-rj"), mutableListOf(
            TagDto(1, "онеме"), TagDto(2, "some cringe")
        )))
    val tags = mutableListOf("Аниме", "Мехи", "Персонажи", "Фильмы")
    Column(modifier = Modifier.fillMaxSize()) {
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(R.string.catalog_string), fontFamily = unboundedBoldFont, fontSize = 30.sp, color = CustomSecondary)
        }
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            CustomTextField(
                description = stringResource(R.string.search_string),
                hint = "",
                textValue = "",
                onValueChanged = {},
                modifier = Modifier.fillMaxWidth(0.8f),
                leadingIcon = Icons.Default.Search,
                backgroundColor = CustomPrimaryContainer,
                cursorColor = CustomAccent)
        }
        LazyRow(modifier = Modifier
            .fillMaxWidth()
            .padding(START_PADDING.dp, 0.dp, END_PADDING.dp, 0.dp)) {
            items(tags.size) {tag ->
                CustomTag(tag = tags[tag], onClick = { /*TODO*/ }, modifiers = Modifier.wrapContentSize().padding(5.dp, 5.dp, 5.dp, 5.dp), spacersSize = 10)
            }
        }
        LazyVerticalGrid(columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(25.dp),
            verticalArrangement = Arrangement.spacedBy(25.dp),
            contentPadding =  PaddingValues(30.dp, 10.dp),
            modifier = Modifier.fillMaxSize()
            ) {
            items(figure.size) {figureId ->
                FigureCard(figure[figureId])
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun CatalogScreenPreview() {
    CatalogScreen()
}