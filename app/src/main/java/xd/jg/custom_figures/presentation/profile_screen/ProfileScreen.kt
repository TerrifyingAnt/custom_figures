package xd.jg.custom_figures.presentation.profile_screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardBackspace
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import xd.jg.custom_figures.R
import xd.jg.custom_figures.presentation.components.CustomButton
import xd.jg.custom_figures.presentation.navigation.Routes
import xd.jg.custom_figures.presentation.register_screen.components.ProfileContent
import xd.jg.custom_figures.presentation.register_screen.components.ProfileLoading
import xd.jg.custom_figures.ui.theme.CustomSecondary
import xd.jg.custom_figures.ui.theme.unboundedBoldFont
import xd.jg.custom_figures.utils.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, viewModel: ProfileViewModel = hiltViewModel()) {
    LaunchedEffect(key1 = Unit) {
        viewModel.getMe()
    }

    BackHandler {
        navController.popBackStack()
    }
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        TopAppBar(
            title = {
                Text(
                    stringResource(R.string.profile_string),
                    fontFamily = unboundedBoldFont,
                    color = CustomSecondary
                )
            },
            navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        Icons.AutoMirrored.Filled.KeyboardBackspace,
                        contentDescription = "go back",
                        tint = CustomSecondary,
                        modifier = Modifier
                            .size(50.dp)
                    )
                }
            }
        )
        when(viewModel.profileUIState.value.userInfo.status) {
            Resource.Status.SUCCESS -> {
                ProfileLoading()
            }
            
            Resource.Status.LOADING -> {
                ProfileContent(navController)

            }
            
            Resource.Status.ERROR -> {
                Column(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxHeight()) {
                    CustomButton(
                        buttonColor = CustomSecondary,
                        buttonText = stringResource(id = R.string.enter_string),
                        onClick = { navController.navigate(Routes.Auth.route) },
                        modifiers = Modifier
                    )
                }
            }
        }

    }
}