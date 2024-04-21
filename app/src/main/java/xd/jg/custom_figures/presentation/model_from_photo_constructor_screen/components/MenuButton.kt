package xd.jg.custom_figures.presentation.model_from_photo_constructor_screen.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import xd.jg.custom_figures.ui.theme.CustomInversePrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuButton(scope: CoroutineScope, scaffoldState: BottomSheetScaffoldState) {
    val transition = updateTransition(targetState = scaffoldState.bottomSheetState.targetValue, label = "LikeButtonTransition")
    val rotation by transition.animateFloat(
        label = "StarRotation",
        transitionSpec = {
            tween(durationMillis = 500, easing = FastOutSlowInEasing)
        }
    ) {
        if (it == SheetValue.Expanded) 360f else -360f
    }
        IconButton(onClick = {
            scope.launch {
                if (scaffoldState.bottomSheetState.currentValue != SheetValue.Expanded) {
                    scaffoldState.bottomSheetState.expand()
                } else {
                    scaffoldState.bottomSheetState.hide()
                }
            }
        }) {Icon(Icons.Rounded.Settings,
            contentDescription = "Localized description",
            tint = CustomInversePrimary,
            modifier = Modifier.rotate(rotation)
        )
    }
}