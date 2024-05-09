package xd.jg.custom_figures.presentation.register_screen

import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.Password
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
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
import xd.jg.custom_figures.utils.Resource

@Composable
fun RegisterScreen(navController: NavController, viewModel: RegisterViewModel = hiltViewModel()) {


    when (viewModel.registerUIState.value.successAuth.status) {
        Resource.Status.SUCCESS -> {
            navController.navigate(BottomNavigationItems.CatalogScreen.route) {
                popUpTo(0)
            }
        }
        Resource.Status.LOADING -> {

        }
        Resource.Status.ERROR -> {
            Toast.makeText(LocalContext.current, viewModel.registerUIState.value.successAuth.message, Toast.LENGTH_SHORT).show()
        }
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxHeight(0.8f)) {
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Text(text = stringResource(R.string.registration_string),  fontFamily = unboundedBoldFont, fontSize = 30.sp, color = CustomSecondary)
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
        LazyColumn {
            item {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CustomTextField(
                        description = stringResource(R.string.your_name_string),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(START_PADDING.dp, 0.dp, END_PADDING.dp, 5.dp),
                        hint = "Артём",
                        textValue = viewModel.registerUIState.value.fullName.value,
                        onValueChanged = viewModel::updateFullName,
                        trailingIcon = Icons.Default.AccountCircle,
                        onTrailingIconClick = {}
                    )
                }
            }
            item {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CustomTextField(
                        description = stringResource(R.string.login_string),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(START_PADDING.dp, 0.dp, END_PADDING.dp, 5.dp),
                        hint = "xd@xd.xd",
                        textValue = viewModel.registerUIState.value.login.value,
                        onValueChanged = viewModel::updateLogin,
                        trailingIcon = Icons.Default.AlternateEmail,
                        onTrailingIconClick = {}
                    )
                }
            }
            item {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CustomTextField(
                        description = stringResource(R.string.password_string),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(START_PADDING.dp, 0.dp, END_PADDING.dp, 5.dp),
                        hint = "qwerty12345",
                        textValue = viewModel.registerUIState.value.password.value,
                        onValueChanged = viewModel::updatePassword,
                        trailingIcon = Icons.Default.Password,
                        onTrailingIconClick = {}
                    )
                }
            }
            item {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CustomTextField(
                        description = stringResource(R.string.repeat_password_string),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(START_PADDING.dp, 0.dp, END_PADDING.dp, 5.dp),
                        hint = "qwerty12345",
                        textValue = viewModel.registerUIState.value.repeatPassword.value,
                        onValueChanged = viewModel::updateCopyPassword,
                        trailingIcon = Icons.Default.Password,
                        visualTransformation = PasswordVisualTransformation(),
                        onTrailingIconClick = {}
                    )
                }
            }
        }

    }

    Column(verticalArrangement = Arrangement.Bottom, modifier = Modifier.fillMaxHeight()) {
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            CustomButton(
                buttonColor = CustomSecondaryContainer,
                buttonText = stringResource(R.string.register_string),
                {
                    if (viewModel.registerUIState.value.isPasswordValid.value &&
                        viewModel.registerUIState.value.isLoginValid.value &&
                        viewModel.registerUIState.value.password.value == viewModel.registerUIState.value.repeatPassword.value &&
                        viewModel.registerUIState.value.fullName.value.isNotEmpty()
                    ){
                        viewModel.register()
                    }
                },
                modifiers = Modifier
                    .fillMaxWidth()
                    .padding(START_PADDING.dp, 0.dp, END_PADDING.dp, 5.dp)
            )
        }
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Text(text="Уже есть аккаунт? ", fontFamily = robotoRegularFont, fontSize = 18.sp, color = CustomSecondary, fontWeight = FontWeight.Bold)
            Text(text="Войти!", fontFamily = robotoRegularFont, fontSize = 18.sp, color = CustomPrimary, fontWeight = FontWeight.Bold, modifier = Modifier.clickable {
                navController.navigate(Routes.Auth.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                }
            })
        }
        Spacer(modifier = Modifier.size(20.dp))
    }
}
