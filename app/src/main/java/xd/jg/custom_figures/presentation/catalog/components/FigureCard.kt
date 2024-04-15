package xd.jg.custom_figures.presentation.catalog.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import xd.jg.custom_figures.ui.theme.CustomPrimary
import xd.jg.custom_figures.utils.Constants.ROUNDED

@Composable
fun FigureCard() {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = CustomPrimary,
        ),
        modifier = Modifier.height(105.dp).width(75.dp).border(1.dp, CustomPrimary, RoundedCornerShape(ROUNDED.dp)),
        shape = RoundedCornerShape(ROUNDED.dp),
    ) {
        Surface(modifier = Modifier.height(75.dp).width(75.dp),
            shape = RoundedCornerShape(ROUNDED.dp)) {
            Image(
                painter = rememberImagePainter("https://i.redd.it/o0vqjo4mcyj21.png"),
                contentDescription = "test_image",
                Modifier.fillMaxWidth()
            )

        }
    }
}

@Preview
@Composable
fun FigureCardPreview() {
    FigureCard()
}