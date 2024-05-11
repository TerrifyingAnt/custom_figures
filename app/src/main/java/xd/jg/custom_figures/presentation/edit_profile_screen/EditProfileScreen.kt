package xd.jg.custom_figures.presentation.edit_profile_screen

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardBackspace
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import xd.jg.custom_figures.R
import xd.jg.custom_figures.presentation.components.CustomButton
import xd.jg.custom_figures.presentation.components.CustomTextField
import xd.jg.custom_figures.ui.theme.CustomSecondary
import xd.jg.custom_figures.ui.theme.CustomSecondaryContainer
import xd.jg.custom_figures.ui.theme.robotoRegularFont
import xd.jg.custom_figures.ui.theme.unboundedBoldFont
import xd.jg.custom_figures.utils.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(navController: NavController, userEmail: String, userName: String, userPhone: String, viewModel: EditProfileViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val applicationContext = LocalContext.current.applicationContext

    if (viewModel.editProfileUIState.value.oldUserEmail.value != userEmail) {
        viewModel.updateOldUserInfo(userName, userEmail.split("&").first(), userPhone)
    }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        val nonNullUri = it
        if (nonNullUri != null) {
                viewModel.updateAvatarSourcePath(applicationContext, nonNullUri)
        }
        else {
            Toast.makeText(context, "xd", Toast.LENGTH_SHORT).show()
        }
    }

    BackHandler {
        navController.popBackStack()
    }

    when {
        viewModel.editProfileUIState.value.avatarUpdated.status == Resource.Status.SUCCESS -> {
            Toast.makeText(context, "Аватарка обновлена", Toast.LENGTH_SHORT).show()
            viewModel.updateAvatarResult(false)
        }
        viewModel.editProfileUIState.value.avatarUpdated.status == Resource.Status.ERROR -> {
            Toast.makeText(context, "Попробуйте другое фото", Toast.LENGTH_SHORT).show()
            viewModel.updateAvatarResult(false)
        }
        viewModel.editProfileUIState.value.nameUpdated.status == Resource.Status.SUCCESS -> {
            Toast.makeText(context, "Информация обновлена", Toast.LENGTH_SHORT).show()
            viewModel.updateToDefaultPhoneAndName()
        }
        viewModel.editProfileUIState.value.phoneUpdated.status == Resource.Status.SUCCESS -> {
            Toast.makeText(context, "Информация обновлена", Toast.LENGTH_SHORT).show()
            viewModel.updateToDefaultPhoneAndName()
        }
    }


    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        TopAppBar(
            title = {
                Text(
                    stringResource(R.string.edit_string),
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
        LazyColumn(modifier = Modifier.fillMaxWidth(0.8f)) {

            item {
                Text(stringResource(R.string.edit_name_string), fontFamily = robotoRegularFont, fontSize = 20.sp)
            }
            item {
                CustomTextField(
                    description = "Имя",
                    hint = "Ваше имя",
                    textValue = viewModel.editProfileUIState.value.newUserName.value,
                    onValueChanged = viewModel::updateUserName
                )
            }
            item {
                Spacer(Modifier.size(20.dp))
            }

            item {
                Text(stringResource(R.string.edit_phone_string), fontFamily = robotoRegularFont, fontSize = 20.sp)
            }
            item {
                CustomTextField(
                    description = "Телефон",
                    hint = "Ваш телефон",
                    textValue = viewModel.editProfileUIState.value.newUserPhone.value,
                    onValueChanged = viewModel::updateUserPhone
                )
            }
            item {
                Spacer(Modifier.size(20.dp))
            }

            item {
                CustomButton(
                    buttonColor = CustomSecondaryContainer,
                    buttonText = "Изменить аватар",
                    onClick = {  launcher.launch("image/*")},
                    modifiers = Modifier.fillMaxWidth()
                )
            }
        }
        Column(modifier = Modifier.fillMaxSize(0.8f), verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.CenterHorizontally) {
            CustomButton(
                buttonColor = CustomSecondaryContainer,
                buttonText = "Сохранить изменения",
                onClick = {  viewModel.saveChanges() },
                modifiers = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.size(5.dp))
        }
    }
}