package xd.jg.custom_figures.presentation.main_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.skydoves.colorpicker.compose.AlphaTile
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import xd.jg.custom_figures.presentation.main_screen.MainScreenViewModel


@Composable
fun ColorPicker(mainScreenViewModel: MainScreenViewModel = hiltViewModel()
) {

    Column(modifier = Modifier.fillMaxSize()) {
        val controller = rememberColorPickerController()
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Text("Выбор цвета", fontSize = 20.sp, fontFamily = FontFamily.SansSerif)
        }
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            HsvColorPicker(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .fillMaxHeight(0.3f)
                    .padding(5.dp),
                controller = controller,
                onColorChanged = { colorEnvelope: ColorEnvelope ->
                    mainScreenViewModel.changeSkyBoxColor(colorEnvelope.color)
                }
            )
        }
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            BrightnessSlider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .height(35.dp),
                controller = controller,
            )
        }
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Text("#${Integer.toHexString(controller.selectedColor.value.toArgb()).uppercase()}", color=controller.selectedColor.value)
        }
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            AlphaTile(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(6.dp)),
                controller = controller,
                selectedColor = controller.selectedColor.value
            )
        }
    }
}


