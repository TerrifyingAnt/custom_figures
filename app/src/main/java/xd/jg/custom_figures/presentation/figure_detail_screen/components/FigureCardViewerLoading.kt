package xd.jg.custom_figures.presentation.figure_detail_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import xd.jg.custom_figures.R
import xd.jg.custom_figures.presentation.figure_detail_screen.FigureDetailViewModel
import xd.jg.custom_figures.ui.theme.unboundedBoldFont

@Composable
fun FigureCardViewerLoading(viewModel: FigureDetailViewModel = hiltViewModel()) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth(0.9f)){
            Text(
                text = stringResource(R.string.figure_is_loading_string),
                fontSize = 20.sp,
                fontFamily = unboundedBoldFont,
                color = Color.White
            )
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            val currentProgress =
                viewModel.figureDetailUIState.value.figureModel.data?.toFloat()
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize(0.8f)
            ) {
                if (currentProgress != null && currentProgress != 0f) {
                    CircularProgressIndicator(
                        progress = currentProgress.toFloat(),
                        color = Color.White,
                        strokeCap = StrokeCap.Round,
                        strokeWidth = 6.dp,
                        modifier = Modifier
                            .fillMaxWidth(0.4f)
                            .fillMaxHeight(0.2f)
                    )
                    Text(
                        text = "${(currentProgress.toFloat() * 100).toInt()} %",
                        fontSize = 24.sp,
                        fontFamily = unboundedBoldFont,
                        color = Color.White
                    )
                } else {
                    CircularProgressIndicator(
                        color = Color.White,
                        strokeCap = StrokeCap.Round,
                        strokeWidth = 6.dp,
                        modifier = Modifier
                            .fillMaxWidth(0.4f)
                            .fillMaxHeight(0.2f)
                    )
                }
            }
        }
    }
}