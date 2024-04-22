package xd.jg.custom_figures.presentation.model_from_photo_constructor_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import xd.jg.custom_figures.presentation.model_from_photo_constructor_screen.ModelFromPhotoConstructorViewModel
import xd.jg.custom_figures.ui.theme.CustomError
import xd.jg.custom_figures.ui.theme.CustomPrimary
import xd.jg.custom_figures.ui.theme.unboundedRegularFont
import xd.jg.custom_figures.utils.Constants.ROUNDED


@Composable
fun MenuContent(viewModel: ModelFromPhotoConstructorViewModel = hiltViewModel()
) {
    val controller = rememberColorPickerController()
    controller.selectCenter(false)
    controller.selectByColor(viewModel.modelFromPhotoConstructorUIState.value.skyBoxColor.value, false)
    Column(modifier = Modifier.fillMaxSize())
    {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("Выбор цвета", fontFamily = unboundedRegularFont, fontSize = 20.sp, color = CustomPrimary)
            Surface(shape = RoundedCornerShape(ROUNDED.dp),
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(20.dp)
                    .clickable { viewModel.updateIsDialogShown() },
                color = viewModel.modelFromPhotoConstructorUIState.value.skyBoxColor.value) {
                controller.selectByColor(viewModel.modelFromPhotoConstructorUIState.value.skyBoxColor.value, true)
            }
        }
        Divider()
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("Вращение модели", fontFamily = unboundedRegularFont, fontSize = 20.sp, color = CustomPrimary)
            Switch(checked = viewModel.modelFromPhotoConstructorUIState.value.isModelRotating.value, onCheckedChange = {
                viewModel.updateIsModelRotating(!viewModel.modelFromPhotoConstructorUIState.value.isModelRotating.value)
            })
        }
        Divider()
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            TextButton({ viewModel.deleteModel() }) {
                Text(text = "Удалить модель", fontFamily = unboundedRegularFont, color = CustomError, fontSize = 20.sp)
            }
        }
    }

    when (viewModel.modelFromPhotoConstructorUIState.value.isDialogShown.value) {
         true -> {
             ColorPickerDialog(controller)
         }
        false -> {}
    }
}

