package xd.jg.custom_figures.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xd.jg.custom_figures.ui.theme.CustomPrimary
import xd.jg.custom_figures.ui.theme.CustomTertiary
import xd.jg.custom_figures.ui.theme.CustomTertiaryContainer
import xd.jg.custom_figures.ui.theme.robotoRegularFont
import xd.jg.custom_figures.utils.Constants.ROUNDED

@Composable
fun CustomTextField(
    description: String,
    modifier: Modifier = Modifier,
    hint: String,
    textValue: String,
    keyboardType: KeyboardType = KeyboardType.Ascii,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChanged: (String) -> Unit,
    trailingIcon: ImageVector? = null,
    leadingIcon: ImageVector? = null,
    onTrailingIconClick: (() -> Unit)? = null,
    backgroundColor: Color? = null,
    cursorColor: Color? = null
) {
    Column(
        modifier = modifier
    ) {

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 3.dp)
                .height(60.dp),
            label = { Text(text = description, color = cursorColor ?: CustomTertiary, fontFamily = robotoRegularFont) },
            value = textValue,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = backgroundColor ?: White,
                unfocusedContainerColor = backgroundColor ?: White,
                disabledContainerColor = backgroundColor ?: White,
                cursorColor = cursorColor ?: CustomPrimary,
                focusedIndicatorColor = cursorColor ?: CustomPrimary,
                unfocusedIndicatorColor = CustomTertiaryContainer,
            ),
            onValueChange = onValueChanged,
            shape = RoundedCornerShape(ROUNDED.dp),
            singleLine = true,
            leadingIcon = {if(leadingIcon != null){
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = cursorColor ?: CustomPrimary
                )
            }
            },
            trailingIcon = {
                if(trailingIcon != null){
                    Icon(
                        imageVector = trailingIcon,
                        contentDescription = null,
                        tint = CustomTertiary,
                        modifier = Modifier
                            .clickable {
                                if(onTrailingIconClick != null) onTrailingIconClick()
                            }
                    )
                }
            },
            placeholder = {
                Text(
                    hint,
                    fontFamily = robotoRegularFont,
                    color = CustomTertiaryContainer
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            visualTransformation = visualTransformation
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CustomTextFieldPreview() {
    CustomTextField(
        description = "Логин",
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 0.dp, 10.dp, 5.dp),
        hint = "xd@xd.xd",
        textValue = "",
        onValueChanged = {},
        trailingIcon = Icons.Default.RemoveRedEye,
        onTrailingIconClick = { /*TODO*/ })
}