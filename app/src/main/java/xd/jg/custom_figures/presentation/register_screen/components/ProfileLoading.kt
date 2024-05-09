package xd.jg.custom_figures.presentation.register_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer
import xd.jg.custom_figures.ui.theme.CustomPrimaryContainer
import xd.jg.custom_figures.utils.Constants.ROUNDED

@Composable
fun ProfileLoading() {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier.fillMaxWidth(0.4f)) {
            Surface(shape = CircleShape, modifier = Modifier.fillMaxWidth()) {
                CircularProgressIndicator(
                    color = Color.White,
                    strokeCap = StrokeCap.Round,
                    strokeWidth = 6.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
        Row(modifier = Modifier.run { fillMaxWidth(0.8f).clip(RoundedCornerShape(ROUNDED)).background(CustomPrimaryContainer).shimmer() }, horizontalArrangement = Arrangement.Center) {
            Box(modifier = Modifier.height(40.dp))
        }
    }
}
