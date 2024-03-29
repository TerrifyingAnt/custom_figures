package xd.jg.custom_figures.presentation.main_screen.models

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import xd.jg.custom_figures.presentation.main_screen.components.ColorPicker
import xd.jg.custom_figures.presentation.main_screen.components.MainScreenContent
import xd.jg.custom_figures.presentation.main_screen.components.MenuButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenTemplate(

) {
    val bottomSheetState = rememberStandardBottomSheetState(
        skipHiddenState = false
    )
    val scaffoldState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState(bottomSheetState=bottomSheetState)
    val scope = rememberCoroutineScope()
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f) // Set the maximum height of the bottom sheet
                    .padding(16.dp)
            ) {
                ColorPicker()
            }
        },
        sheetPeekHeight = 0.dp,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        Box() {
            MainScreenContent()
        }
        MenuButton(scope, scaffoldState)
    }
}