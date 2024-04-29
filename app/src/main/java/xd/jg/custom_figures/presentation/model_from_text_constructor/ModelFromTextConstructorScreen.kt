package xd.jg.custom_figures.presentation.model_from_text_constructor

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import xd.jg.custom_figures.presentation.model_from_text_constructor.components.ModelFromTextContent
import xd.jg.custom_figures.presentation.navigation.BottomNavigationItems
import xd.jg.custom_figures.ui.theme.CustomSecondary
import xd.jg.custom_figures.ui.theme.unboundedBoldFont

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModelFromTextConstructorScreen(
    navController: NavController,
    viewModel: ModelFromTextConstructorViewModel = hiltViewModel()) {

    when {
        viewModel.modelFromPhotoConstructorUIState.value.successfullyAdded.value -> {
            navController.popBackStack()
        }
        viewModel.modelFromPhotoConstructorUIState.value.error.value -> {
            Toast.makeText(LocalContext.current, "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show()
            viewModel.updateError(false)
        }
    }

    BackHandler {
        navController.popBackStack(BottomNavigationItems.CatalogScreen.route, false)
    }
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        TopAppBar(
            title = {
                Text(
                    "Текстовый конструктор",
                    color = CustomSecondary,
                    fontFamily = unboundedBoldFont,
                    fontSize = 28.sp
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack(BottomNavigationItems.CatalogScreen.route, false) }) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "go fucking back",
                        tint = CustomSecondary,
                        modifier = Modifier.size(50.dp)
                    )
                }
            }
        )
        ModelFromTextContent()
    }


}
