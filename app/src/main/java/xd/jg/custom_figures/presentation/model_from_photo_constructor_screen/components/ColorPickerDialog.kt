package xd.jg.custom_figures.presentation.model_from_photo_constructor_screen.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.skydoves.colorpicker.compose.AlphaTile
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.ColorPickerController
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import xd.jg.custom_figures.presentation.model_from_photo_constructor_screen.ModelFromPhotoConstructorViewModel
import xd.jg.custom_figures.ui.theme.CustomError
import xd.jg.custom_figures.ui.theme.CustomPrimary
import xd.jg.custom_figures.ui.theme.CustomPrimaryContainer
import xd.jg.custom_figures.ui.theme.unboundedBoldFont
import xd.jg.custom_figures.utils.Constants

@Composable
fun ColorPickerDialog(
    controller: ColorPickerController,
    viewModel: ModelFromPhotoConstructorViewModel = hiltViewModel()
) {
    Dialog(onDismissRequest = { viewModel.updateIsDialogShown() }) {
        Card(shape = RoundedCornerShape(Constants.ROUNDED.dp), colors = CardDefaults.cardColors(
            containerColor = CustomPrimaryContainer)) {
            Log.d("FUCKING CONTROLLER", controller.selectedColor.value.toString() + " " + viewModel.modelFromPhotoConstructorUIState.value.skyBoxColor.value.toString())

            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                Text(
                    "Выбор цвета",
                    fontSize = 20.sp,
                    fontFamily = unboundedBoldFont,
                    color = CustomPrimary
                )
            }
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                HsvColorPicker(
                    controller = controller,
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .fillMaxHeight(0.3f)
                        .padding(5.dp),
                    onColorChanged = { colorEnvelope: ColorEnvelope ->
                        if (colorEnvelope.fromUser) {
                            viewModel.changeSkyBoxColor(colorEnvelope.color)
                        }
                    },
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
                Text(
                    "#${Integer.toHexString(controller.selectedColor.value.toArgb()).uppercase()}",
                    color = controller.selectedColor.value,
                    fontFamily = unboundedBoldFont,
                    fontSize = 16.sp
                )
            }
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                AlphaTile(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(6.dp)),
                    controller = controller,
                )
            }
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                TextButton(onClick = {
                    viewModel.changeSkyBoxColor(Color(0xFFFFFFFF))
                    viewModel.updateIsDialogShown()
                }) {
                    Text("Цвет по-умолчанию", fontFamily = unboundedBoldFont, color = CustomError)
                }
                TextButton(onClick = { viewModel.updateIsDialogShown() }) {
                    Text("Ок", fontFamily = unboundedBoldFont, color = CustomPrimary)
                }
            }
        }
    }
}