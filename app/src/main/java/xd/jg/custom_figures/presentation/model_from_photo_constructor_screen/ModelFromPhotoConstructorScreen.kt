package xd.jg.custom_figures.presentation.model_from_photo_constructor_screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import xd.jg.custom_figures.R
import xd.jg.custom_figures.presentation.model_from_photo_constructor_screen.components.ColorPicker
import xd.jg.custom_figures.presentation.model_from_photo_constructor_screen.components.MenuButton
import xd.jg.custom_figures.presentation.model_from_photo_constructor_screen.components.ModelFromPhotoConstructorContent
import xd.jg.custom_figures.presentation.navigation.BottomNavigationItems
import xd.jg.custom_figures.ui.theme.CustomPrimary
import xd.jg.custom_figures.ui.theme.CustomSecondary
import xd.jg.custom_figures.ui.theme.unboundedBoldFont

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModelFromPhotoConstructorScreen(
    navController: NavHostController,
    viewModel: ModelFromPhotoConstructorViewModel = hiltViewModel()
) {
    val bottomSheetState = rememberStandardBottomSheetState(
        skipHiddenState = false
    )
    val scaffoldState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState(bottomSheetState=bottomSheetState)
    val scope = rememberCoroutineScope()
    when {
        viewModel.modelFromPhotoConstructorUIState.value.canGo.value -> {
            navController.popBackStack(BottomNavigationItems.CatalogScreen.route, false)
        }
    }


    BackHandler {
        viewModel.clearScene()
    }

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
        sheetContainerColor = CustomPrimary,
        sheetPeekHeight = 0.dp,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(title = {
                Text(
                    stringResource(R.string.constructor_string),
                    fontFamily = unboundedBoldFont,
                    fontSize = 28.sp,
                    color = CustomSecondary
                )
            },
                navigationIcon = {
                    IconButton(onClick = { viewModel.clearScene() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "go fucking back",
                            tint = CustomSecondary,
                            modifier = Modifier.size(50.dp)
                        )
                    }
                },
                actions = { MenuButton(scope, scaffoldState) }
            )
            Box() {
                ModelFromPhotoConstructorContent()
            }
        }
    }
}