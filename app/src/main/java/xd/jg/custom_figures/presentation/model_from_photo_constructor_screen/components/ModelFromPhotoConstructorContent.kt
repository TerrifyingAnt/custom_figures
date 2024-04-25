package xd.jg.custom_figures.presentation.model_from_photo_constructor_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import xd.jg.custom_figures.presentation.components.Counter
import xd.jg.custom_figures.presentation.model_from_photo_constructor_screen.ModelFromPhotoConstructorViewModel
import xd.jg.custom_figures.ui.theme.CustomPrimary
import xd.jg.custom_figures.ui.theme.unboundedRegularFont
import xd.jg.custom_figures.utils.Constants.ROUNDED
import xd.jg.custom_figures.utils.Resource
import xd.jg.custom_figures.utils.Utils

@Composable
fun ModelFromPhotoConstructorContent(
    viewModel: ModelFromPhotoConstructorViewModel = hiltViewModel()
) {
    val context = LocalContext.current.applicationContext
    LaunchedEffect(Unit) {
        viewModel.checkIfPhotoWasMade(context)
    }

    when {
        viewModel.modelFromPhotoConstructorUIState.value.clearScene.value -> {
            viewModel.updateCanGoState()
        }
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
    Column(verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxHeight()) {
        if (viewModel.modelFromPhotoConstructorUIState.value.photoWasMade.value ||
            viewModel.modelFromPhotoConstructorUIState.value.figure.status == Resource.Status.SUCCESS
        ) {
            CurrentModelState(
                currentModelState = Utils.createTestData()
            )
        }
        when {
            viewModel.modelFromPhotoConstructorUIState.value.photoWasMade.value -> {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Spacer(modifier = Modifier.size(5.dp))
                    when {
                        viewModel.modelFromPhotoConstructorUIState.value.count.value == 0 -> {
                            Button(onClick = {viewModel.addToBasket()}, shape = RoundedCornerShape(ROUNDED.dp)) {
                                Text("В корзину!")
                            }
                        }
                        else -> {
                            Counter(viewModel.modelFromPhotoConstructorUIState.value.count.value,
                                { viewModel.addButton() },
                                { viewModel.subtractButton() },
                                textSize = 18,
                                inverse = true)
                        }
                    }

                    CenteredInRowButton(0.1f, 0.5f, "Сделать фото")
                    Spacer(modifier = Modifier.size(5.dp))
                }
            }
            else -> {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    CenteredInRowButton(0.1f, 0.5f, "Сделать фото")
                }
            }
        }

    }
}


