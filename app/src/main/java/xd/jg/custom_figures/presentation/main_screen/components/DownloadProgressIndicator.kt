package xd.jg.custom_figures.presentation.main_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import xd.jg.custom_figures.utils.Resource

@Composable
fun DownloadProgressIndicator(
    modelState: State<Resource<String>>
) {
    when (modelState.value.status) {
        Resource.Status.LOADING -> {
            val currentProgress = modelState.value.data?: return
            Column(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxHeight().background(Color(0x99999999))) {
                Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                    if (currentProgress.toFloat() != 0f) {
                        CircularProgressIndicator(
                            progress = currentProgress.toFloat(),
                            color = Color.LightGray,
                            strokeCap = StrokeCap.Round,
                            strokeWidth = 6.dp,
                            modifier = Modifier
                                .fillMaxSize(0.7f)
                        )
                    }
                    else {
                        CircularProgressIndicator(
                            color = Color.LightGray,
                            strokeCap = StrokeCap.Round,
                            strokeWidth = 6.dp,
                            modifier = Modifier
                                .fillMaxSize(0.7f)
                        )
                    }
                }
            }
        }
        else -> {

        }
    }
}