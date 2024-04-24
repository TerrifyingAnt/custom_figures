package xd.jg.custom_figures.presentation.model_from_photo_constructor_screen.components

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import xd.jg.custom_figures.presentation.model_from_photo_constructor_screen.ModelFromPhotoConstructorViewModel
import xd.jg.custom_figures.ui.theme.CustomTertiary
import xd.jg.custom_figures.utils.Constants.ROUNDED

@Composable
fun CenteredInRowButton(rowHeight: Float, buttonSize: Float, buttonText: String, modelFromPhotoConstructorViewModel: ModelFromPhotoConstructorViewModel = hiltViewModel()) {
    val applicationContext = LocalContext.current.applicationContext
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        if (bitmap != null) {
            modelFromPhotoConstructorViewModel.savePhoto(context, applicationContext, bitmap)
        } else {
            Toast.makeText(context, "Что-то пошло не так", Toast.LENGTH_SHORT).show()
        }
    }

        Button(
            shape = RoundedCornerShape(ROUNDED.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = CustomTertiary
            ),
            onClick = {
                launcher.launch(null) // Launch the camera activity
            },
            modifier = Modifier.fillMaxWidth(buttonSize)
        ) {
            Text(buttonText)
        }

}