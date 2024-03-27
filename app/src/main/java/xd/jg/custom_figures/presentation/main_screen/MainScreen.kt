package xd.jg.custom_figures.presentation.main_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import xd.jg.custom_figures.presentation.main_screen.components.CenteredInRowButton
import xd.jg.custom_figures.presentation.main_screen.components.CurrentModelState
import xd.jg.custom_figures.presentation.main_screen.components.FullModelViewer
import xd.jg.custom_figures.utils.Resource
import xd.jg.custom_figures.utils.Utils

@Composable
fun MainScreen(
    mainScreenViewModel: MainScreenViewModel = hiltViewModel()
) {
    val photoWasMade = mainScreenViewModel.mainScreenUIState.value.photoWasMade
    val previousModel = mainScreenViewModel.launchModelOnStartup.collectAsState()
    var buttonText = "Сделать фото"

    when(previousModel.value.status) {
        Resource.Status.SUCCESS -> {
            if (previousModel.value.data == true) {
                FullModelViewer(fraction = 0.85f, previousModel.value.data)
            }
            else {
                Text("Сделай фоту -_-")
            }
        }
        Resource.Status.LOADING -> {
            Text("Сделай фоту -_-")
        }
        Resource.Status.ERROR -> {
            Text("Сделай фоту -_-")
        }
    }
    Column(verticalArrangement = Arrangement.Bottom, modifier = Modifier.fillMaxHeight()) {
        if (photoWasMade || previousModel.value.data == true) {
            CurrentModelState(
                currentModelState = Utils.createTestData()
            )
            buttonText = "Переснять"
        }
        CenteredInRowButton( 0.1f, 0.5f, buttonText)

    }
}

