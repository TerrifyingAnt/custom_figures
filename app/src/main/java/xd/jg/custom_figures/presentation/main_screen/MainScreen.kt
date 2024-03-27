package xd.jg.custom_figures.presentation.main_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import xd.jg.custom_figures.presentation.main_screen.components.CenteredInRowButton
import xd.jg.custom_figures.presentation.main_screen.components.CurrentModelState
import xd.jg.custom_figures.presentation.main_screen.components.FullModelViewer
import xd.jg.custom_figures.utils.Utils

@Composable
fun MainScreen(
    mainScreenViewModel: MainScreenViewModel = hiltViewModel()
) {

    val photoWasMade = mainScreenViewModel.mainScreenUIState.value.photoWasMade

    if (photoWasMade) {
        FullModelViewer(fraction = 0.85f)
    }
    Column(verticalArrangement = Arrangement.Bottom, modifier = Modifier.fillMaxHeight()) {
        if (photoWasMade) {
            CurrentModelState(
                currentModelState = Utils.createTestData()
            )
        }
        else {
            CenteredInRowButton( 0.1f, 0.5f)
        }
    }
}

