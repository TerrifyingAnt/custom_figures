package xd.jg.custom_figures.presentation.catalog.components

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import xd.jg.custom_figures.ui.theme.CustomAccent
import xd.jg.custom_figures.ui.theme.CustomOnPrimaryContainer
import xd.jg.custom_figures.ui.theme.CustomPrimary
import xd.jg.custom_figures.ui.theme.CustomPrimaryContainer
import xd.jg.custom_figures.ui.theme.robotoRegularFont
import xd.jg.custom_figures.utils.Constants

@Composable
fun CustomTag(tag: String, onClick: () -> Unit, isPressed: Boolean? = false, modifiers: Modifier) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(Constants.ROUNDED.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isPressed == true) CustomPrimary else CustomPrimaryContainer,
            contentColor = CustomOnPrimaryContainer
        ),
        modifier = modifiers
    ) {
        Text(tag, fontFamily = robotoRegularFont, fontSize = 20.sp, color = if (isPressed == true) Color.White else CustomAccent)
    }
}

@Preview
@Composable
fun CustomTagPreview() {
    CustomTag(tag = "xd", {}, false, Modifier.wrapContentSize())
}