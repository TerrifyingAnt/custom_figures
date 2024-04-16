package xd.jg.custom_figures.presentation.main_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import xd.jg.custom_figures.presentation.main_screen.MainScreenViewModel
import xd.jg.custom_figures.utils.Resource
import xd.jg.custom_figures.utils.Utils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenContent(
    mainScreenViewModel: MainScreenViewModel = hiltViewModel()
) {
    val photoWasMade = mainScreenViewModel.mainScreenUIState.value.photoWasMade
    val previousModel = mainScreenViewModel.launchModelOnStartup.collectAsState()
    var buttonText = "Сделать фото"



    Box() {
        when (previousModel.value.status) {
            Resource.Status.SUCCESS -> {
                if (previousModel.value.data == true) {
                    FullModelViewer(baseState = previousModel.value.data)
                } else {
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
            CenteredInRowButton(0.1f, 0.5f, buttonText)
        }
    }
}
