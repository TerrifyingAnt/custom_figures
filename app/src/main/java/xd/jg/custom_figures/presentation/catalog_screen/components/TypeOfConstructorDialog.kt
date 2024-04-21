package xd.jg.custom_figures.presentation.catalog_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import xd.jg.custom_figures.R
import xd.jg.custom_figures.presentation.catalog_screen.CatalogViewModel
import xd.jg.custom_figures.presentation.components.CustomButton
import xd.jg.custom_figures.presentation.navigation.Routes
import xd.jg.custom_figures.ui.theme.CustomOnPrimaryContainer
import xd.jg.custom_figures.ui.theme.CustomPrimaryContainer
import xd.jg.custom_figures.ui.theme.CustomPrimaryFixedDim
import xd.jg.custom_figures.ui.theme.CustomTertiaryFixedDim
import xd.jg.custom_figures.ui.theme.robotoRegularFont
import xd.jg.custom_figures.utils.Constants.ROUNDED

@Composable
fun TypeOfConstructorDialog(
    navController: NavController,
    viewModel: CatalogViewModel = hiltViewModel()
) {
    Dialog(onDismissRequest = {viewModel.updateIsDialogShown()}) {
        Card(shape = RoundedCornerShape(ROUNDED.dp), colors = CardDefaults.cardColors(
            containerColor = CustomPrimaryContainer
        )) {
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                Text(
                    stringResource(id = R.string.which_figure_do_you_want_string),
                    fontFamily = robotoRegularFont,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = CustomOnPrimaryContainer
                )
            }
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                Text(
                    stringResource(id = R.string.which_figure_do_you_want_description_string),
                    fontFamily = robotoRegularFont,
                    fontSize = 16.sp,
                    color = CustomOnPrimaryContainer,
                    modifier = Modifier.fillMaxWidth(0.8f)
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                    CustomButton(
                        buttonColor = CustomPrimaryFixedDim,
                        buttonText = stringResource(id = R.string.text_constructor_string),
                        onClick = { viewModel.updateIsDialogShown() },
                        modifiers = Modifier.fillMaxWidth(0.8f)
                    )
                }
                Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                    CustomButton(
                        buttonColor = CustomPrimaryFixedDim,
                        buttonText = stringResource(id = R.string.photo_constructor_string),
                        onClick = {
                            viewModel.updateIsDialogShown()
                            navController.navigate(Routes.ModelFromPhotoConstructorScreen.route) },
                        modifiers = Modifier.fillMaxWidth(0.8f)
                    )
                }
                Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                    CustomButton(
                        buttonColor = CustomTertiaryFixedDim,
                        buttonText = stringResource(id = R.string.cancel_string),
                        onClick = { viewModel.updateIsDialogShown() },
                        modifiers = Modifier.fillMaxWidth(0.8f)
                    )
                }
                Spacer(Modifier.size(5.dp))
            }
        }
    }
}