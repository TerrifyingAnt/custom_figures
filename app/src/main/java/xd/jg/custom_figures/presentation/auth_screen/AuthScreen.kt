package xd.jg.custom_figures.presentation.auth_screen


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.Password
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import xd.jg.custom_figures.R
import xd.jg.custom_figures.presentation.components.CustomButton
import xd.jg.custom_figures.presentation.components.CustomTextField
import xd.jg.custom_figures.presentation.navigation.BottomNavigationItems
import xd.jg.custom_figures.presentation.navigation.Routes
import xd.jg.custom_figures.ui.theme.CustomPrimary
import xd.jg.custom_figures.ui.theme.CustomSecondary
import xd.jg.custom_figures.ui.theme.CustomSecondaryContainer
import xd.jg.custom_figures.ui.theme.robotoRegularFont
import xd.jg.custom_figures.ui.theme.unboundedBoldFont
import xd.jg.custom_figures.utils.Constants.END_PADDING
import xd.jg.custom_figures.utils.Constants.START_PADDING

@Composable
fun AuthScreen(navController: NavController, viewModel: AuthViewModel = hiltViewModel()) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxHeight(0.8f)) {
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Text(text = stringResource(R.string.authentication_string),  fontFamily = unboundedBoldFont, fontSize = 30.sp, color = CustomSecondary)
        }
        Spacer(Modifier.size(5.dp))
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(R.drawable.ic_custom_heroes),
                contentDescription = "main_image",
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(Modifier.size(20.dp))
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            CustomTextField(
                description = stringResource(R.string.login_string),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 0.dp, 20.dp, 5.dp),
                hint = "xd@xd.xd",
                textValue = viewModel.authUIState.value.login.value,
                onValueChanged = viewModel::updateLogin,
                trailingIcon = Icons.Default.AlternateEmail,
                onTrailingIconClick = {}
            )
        }

        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            CustomTextField(
                description = stringResource(R.string.password_string),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(START_PADDING.dp, 0.dp, END_PADDING.dp, 5.dp),
                hint = "qwerty12345",
                textValue = viewModel.authUIState.value.password.value,
                onValueChanged = viewModel::updatePassword,
                trailingIcon = Icons.Default.Password,
                onTrailingIconClick = {}
            )
        }
    }

    Column(verticalArrangement = Arrangement.Bottom, modifier = Modifier.fillMaxHeight()) {
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            CustomButton(
                buttonColor = CustomSecondaryContainer,
                buttonText = stringResource(R.string.enter_string),
                { navController.navigate(BottomNavigationItems.CatalogScreen.route) {
                    popUpTo(0)
                } },
                modifiers = Modifier
                    .fillMaxWidth()
                    .padding(START_PADDING.dp, 0.dp, END_PADDING.dp, 5.dp)
            )
        }
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Text(text="Нет аккаунта? ", fontFamily = robotoRegularFont, fontSize = 18.sp, color = CustomSecondary, fontWeight = FontWeight.Bold)
            Text(text="Зарегистрироваться!", fontFamily = robotoRegularFont, fontSize = 18.sp, color = CustomPrimary, fontWeight = FontWeight.Bold, modifier = Modifier.clickable {
             navController.navigate(Routes.Register.route)
            })
        }
        Spacer(modifier = Modifier.size(20.dp))

    }
}

