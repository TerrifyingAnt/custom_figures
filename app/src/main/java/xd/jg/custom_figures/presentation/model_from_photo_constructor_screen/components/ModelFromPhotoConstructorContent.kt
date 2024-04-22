package xd.jg.custom_figures.presentation.model_from_photo_constructor_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import xd.jg.custom_figures.presentation.model_from_photo_constructor_screen.ModelFromPhotoConstructorViewModel
import xd.jg.custom_figures.ui.theme.CustomPrimary
import xd.jg.custom_figures.ui.theme.unboundedRegularFont
import xd.jg.custom_figures.utils.Resource
import xd.jg.custom_figures.utils.Utils

@Composable
fun ModelFromPhotoConstructorContent(
    viewModel: ModelFromPhotoConstructorViewModel = hiltViewModel()
) {
    var buttonText = "Сделать фото"
    val context = LocalContext.current.applicationContext
    LaunchedEffect(Unit) {
        viewModel.checkIfPhotoWasMade(context)
    }

    Box() {
        when(viewModel.modelFromPhotoConstructorUIState.value.figure.status) {
            Resource.Status.SUCCESS -> {
                FullModelViewer()
            }
            Resource.Status.LOADING -> {
                Column(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(modifier = Modifier.fillMaxWidth(0.8f)) {
                            Text(
                                "Здесь будет отображена модель по твоей фотографии! Чтобы ее получить, сделай фотографию по кнопке ниже",
                                fontFamily = unboundedRegularFont,
                                fontSize = 24.sp,
                                color = CustomPrimary
                            )
                            when {
                                viewModel.modelFromPhotoConstructorUIState.value.clearScene.value -> {
                                    viewModel.updateCanGoState()
                                }
                            }
                        }
                    }
                }
            }
            Resource.Status.ERROR -> {
                Column(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(modifier = Modifier.fillMaxWidth(0.8f)) {
                            Text(
                                "Произошла ошибка: попробуйте позже",
                                fontFamily = unboundedRegularFont,
                                fontSize = 24.sp,
                                color = CustomPrimary
                            )
                        }
                    }
                }
            }
        }
    }
    Column(verticalArrangement = Arrangement.Bottom, modifier = Modifier.fillMaxHeight()) {
        if (viewModel.modelFromPhotoConstructorUIState.value.photoWasMade.value ||
            viewModel.modelFromPhotoConstructorUIState.value.figure.status == Resource.Status.SUCCESS) {
            CurrentModelState(
                currentModelState = Utils.createTestData()
            )
            buttonText = "Переснять"
        }
        CenteredInRowButton(0.1f, 0.5f, buttonText)
    }
}


