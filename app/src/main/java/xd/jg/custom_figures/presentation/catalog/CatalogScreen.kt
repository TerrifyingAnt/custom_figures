package xd.jg.custom_figures.presentation.catalog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
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
import xd.jg.custom_figures.presentation.catalog.components.CustomTag
import xd.jg.custom_figures.presentation.components.CustomTextField
import xd.jg.custom_figures.ui.theme.CustomAccent
import xd.jg.custom_figures.ui.theme.CustomPrimaryContainer
import xd.jg.custom_figures.ui.theme.CustomSecondary
import xd.jg.custom_figures.ui.theme.unboundedBoldFont
import xd.jg.custom_figures.utils.Constants.END_PADDING
import xd.jg.custom_figures.utils.Constants.START_PADDING

@Composable
fun CatalogScreen() {
    val tags = mutableListOf("xd", "fuck", "well", "nice")
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
            .padding(START_PADDING.dp, 0.dp, END_PADDING.dp, 5.dp)) {
            items(tags.size) {tag ->
                CustomTag(tag = tags.get(tag), onClick = { /*TODO*/ }, modifiers = Modifier.wrapContentSize().padding(5.dp, 5.dp, 5.dp, 5.dp))
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun CatalogScreenPreview() {
    CatalogScreen()
}