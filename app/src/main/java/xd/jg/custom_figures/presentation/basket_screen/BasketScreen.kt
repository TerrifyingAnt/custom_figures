package xd.jg.custom_figures.presentation.basket_screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import xd.jg.custom_figures.R
import xd.jg.custom_figures.presentation.basket_screen.components.BasketContent
import xd.jg.custom_figures.presentation.components.CustomButton
import xd.jg.custom_figures.presentation.navigation.BottomNavigationItems
import xd.jg.custom_figures.presentation.navigation.Routes
import xd.jg.custom_figures.ui.theme.CustomPrimary
import xd.jg.custom_figures.ui.theme.CustomPrimaryFixedDim
import xd.jg.custom_figures.ui.theme.CustomSecondary
import xd.jg.custom_figures.ui.theme.unboundedBoldFont

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasketScreen (
    navController: NavController,
    viewModel: BasketViewModel = hiltViewModel()) {
    LaunchedEffect(Unit) {
        viewModel.getAllBasketItems()
        viewModel.checkIfTokensExist()
    }

    BackHandler {
        navController.popBackStack(BottomNavigationItems.CatalogScreen.route, false)
    }
    Column(modifier = Modifier.fillMaxHeight(0.9f)) {
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Text(
                stringResource(R.string.basket_string),
                fontFamily = unboundedBoldFont,
                fontSize = 30.sp,
                color = CustomSecondary
            )
        }
        BasketContent(navController)

    }
    when {
        (viewModel.basketUIState.value.basketFigures.value?.size ?: 0) > 0 -> {
            Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Bottom) {
                var price = 0
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    price =
                        (viewModel.basketUIState.value.basketFigures.value?.sumOf { it.count * if (it.price.toInt() == 0) 2500 else it.price.toInt() }
                            ?: 0).toInt()
                    Text(
                        "Итого: $price₽",
                        color = CustomPrimary,
                        fontFamily = unboundedBoldFont,
                        fontSize = 26.sp
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CustomButton(
                        buttonColor = CustomPrimaryFixedDim,
                        buttonText = if (viewModel.basketUIState.value.authorized.value) stringResource(
                            R.string.buy_string
                        ) else stringResource(R.string.enter_string),
                        onClick = {
                            if (viewModel.basketUIState.value.authorized.value) {
                                viewModel.createOrder()
                            } else {
                                navController.navigate(Routes.Auth.route)
                            }
                        },
                        bold = true,
                        modifiers = Modifier.fillMaxWidth(0.8f)
                    )
                }
            }
        }
    }
}

