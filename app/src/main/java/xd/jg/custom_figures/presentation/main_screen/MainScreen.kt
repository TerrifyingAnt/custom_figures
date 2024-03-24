package xd.jg.custom_figures.presentation.main_screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import xd.jg.custom_figures.presentation.main_screen.components.CenteredInRowButton
import xd.jg.custom_figures.presentation.main_screen.components.CurrentModelState
import xd.jg.custom_figures.presentation.main_screen.components.FullModelViewer
import xd.jg.custom_figures.presentation.main_screen.models.PartModelData
import xd.jg.custom_figures.utils.Utils

@Composable
fun MainScreen(
    mainScreenViewModel: MainScreenViewModel = hiltViewModel()
) {

    val context = LocalContext.current
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
            CenteredInRowButton(context, 0.1f, 0.5f)
        }
    }
}

