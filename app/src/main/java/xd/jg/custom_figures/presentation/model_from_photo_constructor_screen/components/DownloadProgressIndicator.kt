package xd.jg.custom_figures.presentation.model_from_photo_constructor_screen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.expandIn
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import xd.jg.custom_figures.presentation.model_from_photo_constructor_screen.ModelFromPhotoConstructorViewModel
import xd.jg.custom_figures.ui.theme.CustomCoolGreen
import xd.jg.custom_figures.ui.theme.CustomError
import xd.jg.custom_figures.utils.Resource

@Composable
fun DownloadProgressIndicator(
    modelState: Resource<String>,
    modelFromPhotoConstructorViewModel: ModelFromPhotoConstructorViewModel = hiltViewModel()
) {
    val currentState = MutableTransitionState(false)
    val applicationContext = LocalContext.current.applicationContext

    currentState.targetState = true
    when (modelState.status) {
        Resource.Status.LOADING -> {
                val currentProgress = modelState.data ?: return
                if (currentProgress != "null" && currentProgress.toFloat() != 0f) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0x99999999)),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            progress = currentProgress.toFloat(),
                            color = Color.LightGray,
                            strokeCap = StrokeCap.Round,
                            strokeWidth = 6.dp,
                            modifier = Modifier
                                .fillMaxSize(0.7f)
                        )
                        Text(
                            text = "${(currentProgress.toFloat() * 100).toInt()} %",
                            fontSize = 24.sp,
                            color = Color.LightGray
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0x99999999)),
                        contentAlignment = Alignment.Center
                    ) {
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
        Resource.Status.SUCCESS -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
            AnimatedVisibility(
                visibleState = currentState,
                enter = expandIn(animationSpec = TweenSpec(durationMillis = 500), expandFrom = Alignment.Center),
                exit = shrinkOut(animationSpec = TweenSpec(durationMillis = 500), shrinkTowards = Alignment.Center),
                modifier = Modifier.clip(CircleShape)
            ) {
                    Icon(
                        Icons.Rounded.CheckCircle,
                        contentDescription = "xd",
                        modifier = Modifier.fillMaxSize(),
                        tint = CustomCoolGreen
                    )
                }
            }
            currentState.targetState = false
        }

        Resource.Status.ERROR -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        modelFromPhotoConstructorViewModel.retryDownloadGlbFile(applicationContext, modelState.data ?: "")
                        currentState.targetState = false
                               },
                contentAlignment = Alignment.Center
            ) {
                AnimatedVisibility(
                    visibleState = currentState,
                    enter = expandIn(animationSpec = TweenSpec(durationMillis = 500), expandFrom = Alignment.Center),
                    exit = shrinkOut(animationSpec = TweenSpec(durationMillis = 500), shrinkTowards = Alignment.Center),
                    modifier = Modifier.clip(CircleShape)
                ) {
                    Icon(
                        Icons.Rounded.Warning,
                        contentDescription = "xd",
                        modifier = Modifier.fillMaxSize(),
                        tint = CustomError
                    )
                }
            }
        }
    }

}