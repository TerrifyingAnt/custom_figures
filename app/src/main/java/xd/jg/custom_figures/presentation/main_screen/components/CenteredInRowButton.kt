package xd.jg.custom_figures.presentation.main_screen.components

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import xd.jg.custom_figures.presentation.main_screen.MainScreenViewModel

@Composable
fun CenteredInRowButton(rowHeight: Float, buttonSize: Float, buttonText: String, mainScreenViewModel: MainScreenViewModel = hiltViewModel()) {
    val applicationContext = LocalContext.current.applicationContext
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        if (bitmap != null) {
            mainScreenViewModel.savePhoto(context, applicationContext, bitmap)
        } else {
            Toast.makeText(context, "Что-то пошло не так", Toast.LENGTH_SHORT).show()
        }
    }
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(rowHeight)
    ) {
        Button(
            onClick = {
                launcher.launch(null) // Launch the camera activity
            },
            modifier = Modifier.fillMaxWidth(buttonSize)
        ) {
            Text(buttonText)
        }
    }
}