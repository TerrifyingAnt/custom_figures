package xd.jg.custom_figures.presentation.register_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import xd.jg.custom_figures.presentation.components.CustomButton
import xd.jg.custom_figures.presentation.profile_screen.ProfileViewModel
import xd.jg.custom_figures.ui.theme.CustomPrimaryContainer
import xd.jg.custom_figures.ui.theme.CustomSecondary
import xd.jg.custom_figures.ui.theme.CustomSecondaryContainer
import xd.jg.custom_figures.ui.theme.unboundedBoldFont
import xd.jg.custom_figures.utils.Constants.BASE_AVATAR

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ProfileContent(navController: NavController, viewModel: ProfileViewModel = hiltViewModel()) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier.fillMaxWidth(0.4f)) {
            Surface(shape = CircleShape, modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = rememberImagePainter(
                        viewModel.profileUIState.value.userInfo.data?.userAvatarLink ?: BASE_AVATAR
                    ),
                    contentDescription = "test_image",
                    contentScale = ContentScale.Fit
                )
            }
        }
        Row(modifier = Modifier.fillMaxWidth(0.8f), horizontalArrangement = Arrangement.Center) {
            Text(viewModel.profileUIState.value.userInfo.data?.userFullName ?: "", fontFamily = unboundedBoldFont, color = CustomSecondary, fontSize = 24.sp)
        }
        Spacer(Modifier.size(20.dp))
        Row(modifier = Modifier.fillMaxWidth(0.8f), horizontalArrangement = Arrangement.Center) {
            CustomButton(
                buttonColor = CustomPrimaryContainer,
                buttonText = "Редактировать профиль",
                onClick = { /*TODO*/ },
                modifiers = Modifier.fillMaxWidth()
            )
        }
        Spacer(Modifier.size(20.dp))
        Row(modifier = Modifier.fillMaxWidth(0.8f), horizontalArrangement = Arrangement.Center) {
            CustomButton(
                buttonColor = CustomPrimaryContainer,
                buttonText = "История заказов",
                onClick = { /*TODO*/ },
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
