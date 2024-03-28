package xd.jg.custom_figures.presentation.main_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
        Column(verticalArrangement = Arrangement.Top) {
            Spacer(modifier = Modifier.size(10.dp))
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                Card(shape = RoundedCornerShape(5.dp), modifier = Modifier
                    .size(30.dp)
                    .clickable {

                    }) {
                    Column(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxHeight()) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Rounded.Menu, contentDescription = "Localized description")
                        }
                    }

                }
                Spacer(modifier = Modifier.size(10.dp))
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

