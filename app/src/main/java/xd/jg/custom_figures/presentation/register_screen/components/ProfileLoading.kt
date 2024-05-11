package xd.jg.custom_figures.presentation.register_screen.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.valentinilk.shimmer.shimmer
import xd.jg.custom_figures.presentation.components.CustomButton
import xd.jg.custom_figures.presentation.profile_screen.ProfileViewModel
import xd.jg.custom_figures.ui.theme.CustomPrimary
import xd.jg.custom_figures.ui.theme.CustomPrimaryContainer
import xd.jg.custom_figures.ui.theme.CustomSecondaryContainer
import xd.jg.custom_figures.utils.Constants.ROUNDED

@Composable
fun ProfileLoading(viewModel: ProfileViewModel = hiltViewModel()) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Surface(
            shape = CircleShape,
            color = CustomPrimaryContainer
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.2f)
                    .fillMaxWidth(0.35f)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = CustomPrimary,
                    strokeCap = StrokeCap.Round,
                    strokeWidth = 6.dp,
                    modifier = Modifier.fillMaxSize(0.4f) // Adjust the size of the progress bar
                )
            }
        }
        Spacer(Modifier.size(20.dp))
        Row(modifier = Modifier.run {
            fillMaxWidth(0.8f)
                .clip(RoundedCornerShape(ROUNDED))
                .shimmer()
        }, horizontalArrangement = Arrangement.Center) {
            Box(
                modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth()
                    .background(CustomPrimaryContainer)
            )
        }
        Spacer(Modifier.size(20.dp))
        Row(modifier = Modifier.fillMaxWidth(0.8f), horizontalArrangement = Arrangement.Center) {
            CustomButton(
                buttonColor = CustomPrimaryContainer,
                buttonText = "Редактировать профиль",
                onClick = { Toast.makeText(context, "Данные загружаются, подождите", Toast.LENGTH_SHORT).show() },
                modifiers = Modifier.fillMaxWidth()
            )
        }
        Spacer(Modifier.size(20.dp))
        Row(modifier = Modifier.fillMaxWidth(0.8f), horizontalArrangement = Arrangement.Center) {
            CustomButton(
                buttonColor = CustomPrimaryContainer,
                buttonText = "История заказов",
                onClick = { Toast.makeText(context, "Данные загружаются, подождите", Toast.LENGTH_SHORT).show() },
                modifiers = Modifier.fillMaxWidth()
            )
        }
        Column(verticalArrangement = Arrangement.Bottom, modifier = Modifier.fillMaxHeight()) {
            CustomButton(
                buttonColor = CustomSecondaryContainer,
                buttonText = "Выход",
                onClick = { viewModel.exitProfile() },
                modifiers = Modifier
            )
            Spacer(modifier = Modifier.size(10.dp))
        }
    }
}
