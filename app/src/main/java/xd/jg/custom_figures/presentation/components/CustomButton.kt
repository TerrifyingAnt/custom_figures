package xd.jg.custom_figures.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import xd.jg.custom_figures.ui.theme.CustomOnPrimaryContainer
import xd.jg.custom_figures.ui.theme.CustomSecondaryContainer
import xd.jg.custom_figures.ui.theme.robotoRegularFont
import xd.jg.custom_figures.utils.Constants.ROUNDED

@Composable
fun CustomButton(buttonColor: Color, buttonText: String, onClick: () -> Unit, modifiers: Modifier, bold: Boolean = false) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(ROUNDED.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor,
            contentColor = CustomOnPrimaryContainer
        ),
        modifier = modifiers
    ) {
        Text(buttonText, fontFamily = robotoRegularFont, fontSize = 20.sp, fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal )
    }
}




@Preview
@Composable
fun CustomButtonPreview() {
    CustomButton(CustomSecondaryContainer, "Войти", {}, Modifier.fillMaxWidth().padding(20.dp, 0.dp, 20.dp, 5.dp))
}