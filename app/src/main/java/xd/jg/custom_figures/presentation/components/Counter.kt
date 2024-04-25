package xd.jg.custom_figures.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import xd.jg.custom_figures.ui.theme.CustomCoolGreen
import xd.jg.custom_figures.ui.theme.CustomError
import xd.jg.custom_figures.ui.theme.CustomPrimary
import xd.jg.custom_figures.ui.theme.unboundedRegularFont

@Composable
fun Counter(initialValue: Int = 0, addButton: (Int) -> Unit = {}, subtractButton: (Int) -> Unit = {}, textSize: Int = 16, inverse: Boolean = false) {
    var counter by remember { mutableIntStateOf(initialValue) }
    Row(modifier = Modifier.wrapContentSize(), verticalAlignment = Alignment.CenterVertically) {
        TextButton(onClick = {
            counter -= 1
            subtractButton(counter)
        }) {
            Text("-", color = CustomError, fontFamily = unboundedRegularFont, fontSize = textSize.sp)
        }
        Text("$counter", color = if (!inverse) Color.White else CustomPrimary, fontFamily = unboundedRegularFont, fontSize = textSize.sp)
        TextButton(onClick = {
            counter += 1
            addButton(counter)
        }) {
            Text("+", color = CustomCoolGreen, fontFamily = unboundedRegularFont, fontSize = textSize.sp)
        }
    }
}

@Composable
@Preview
fun CounterPreview() {
    Counter()
}