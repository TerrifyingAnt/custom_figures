package xd.jg.custom_figures.presentation.catalog.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import xd.jg.custom_figures.ui.theme.CustomAccent
import xd.jg.custom_figures.ui.theme.CustomPrimary
import xd.jg.custom_figures.ui.theme.CustomPrimaryContainer
import xd.jg.custom_figures.ui.theme.robotoRegularFont
import xd.jg.custom_figures.utils.Constants

@Composable
fun CustomTag(tag: String, onClick: () -> Unit, isPressed: Boolean? = false, modifiers: Modifier, textSize: Int? = 0, spacersSize: Int? = 0) {
    Surface(
        shape = RoundedCornerShape(Constants.ROUNDED.dp),
        color = if (isPressed == true) CustomPrimary else CustomPrimaryContainer,
        modifier = modifiers.clickable {
            onClick
        }
    ) {
        val spacerVerticalSize = if (spacersSize != 0 && spacersSize != null) spacersSize.dp else 2.dp
        val spacerHorizontalSize = if (spacersSize != 0 && spacersSize != null) spacersSize.dp else 2.dp
        Column(verticalArrangement = Arrangement.Center, modifier = Modifier.padding(0.dp, spacerVerticalSize)) {
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.padding(spacerHorizontalSize, 0.dp)) {
                Text(
                    tag,
                    fontFamily = robotoRegularFont,
                    color = if (isPressed == true) Color.White else CustomAccent,
                    fontSize = if (textSize != 0 && textSize != null) textSize.sp else 16.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun CustomTagPreview() {
    CustomTag(tag = "Аниме", {}, true, Modifier.wrapContentSize())
}