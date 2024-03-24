package xd.jg.custom_figures.presentation.main_screen.components

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import xd.jg.custom_figures.presentation.main_screen.MainScreenViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CenteredInRowButton(context: Context, rowHeight: Float, buttonSize: Float, mainScreenViewModel: MainScreenViewModel = hiltViewModel()) {
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        if (bitmap != null) {
            mainScreenViewModel.savePhoto(context, bitmap)
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
            Text("Сделайте фото")
        }
    }
}