package xd.jg.custom_figures.presentation.main_screen.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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

    Column(verticalArrangement = Arrangement.Top) {
        Spacer(modifier = Modifier.size(10.dp))
        Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
            Card(shape = RoundedCornerShape(5.dp), modifier = Modifier
                .size(30.dp)
                .clickable {
                    scope.launch {
                        if (scaffoldState.bottomSheetState.currentValue != SheetValue.Expanded) {
                            scaffoldState.bottomSheetState.expand()
                        } else {
                            scaffoldState.bottomSheetState.hide()
                        }
                    }
                }) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Rounded.Settings, contentDescription = "Localized description",
                            modifier = Modifier.rotate(rotation))
                    }
                }
            }
            Spacer(modifier = Modifier.size(10.dp))
        }
    }
}