package xd.jg.custom_figures.presentation.order_history_screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardBackspace
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import xd.jg.custom_figures.R
import xd.jg.custom_figures.presentation.order_history_screen.components.OrderItem
import xd.jg.custom_figures.ui.theme.CustomOnPrimaryFixedVariant
import xd.jg.custom_figures.ui.theme.CustomPrimary
import xd.jg.custom_figures.ui.theme.CustomSecondary
import xd.jg.custom_figures.ui.theme.unboundedBoldFont
import xd.jg.custom_figures.utils.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderHistoryScreen(navController: NavController, viewModel: OrderHistoryViewModel = hiltViewModel()) {
    viewModel.getOrders()
    BackHandler {
        navController.popBackStack()
    }

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        TopAppBar(
            title = {
                Text(
                    stringResource(R.string.history_string),
                    fontFamily = unboundedBoldFont,
                    color = CustomSecondary
                )
            },
            navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        Icons.AutoMirrored.Filled.KeyboardBackspace,
                        contentDescription = "go back",
                        tint = CustomSecondary,
                        modifier = Modifier
                            .size(50.dp)
                    )
                }
            }
        )
        when (viewModel.orderHistoryUIState.value.historyData.status) {
            Resource.Status.LOADING -> {
                Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        CircularProgressIndicator(
                            color = CustomPrimary,
                            strokeCap = StrokeCap.Round,
                            strokeWidth = 6.dp,
                            modifier = Modifier.fillMaxWidth(0.2f)
                        )
                    }
                }
            }
            Resource.Status.SUCCESS -> {
                val orders = viewModel.orderHistoryUIState.value.historyData.data ?: return@Column
                val activeOrders = orders.filter { it.orderInfo.state != "DONE" }
                val doneOrders = orders.filter { it.orderInfo.state == "DONE" }
                LazyColumn(Modifier.fillMaxWidth(0.8f), horizontalAlignment = Alignment.CenterHorizontally) {
                    items(activeOrders.size) {
                        if (it == 0) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Text(text = "Активные", color = CustomOnPrimaryFixedVariant, fontFamily = unboundedBoldFont, fontSize = 20.sp)
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            OrderItem(activeOrders[it])
                        }
                        Spacer(modifier = Modifier.size(20.dp))
                        if (activeOrders[it] == activeOrders.last()) {
                            Divider()
                        }
                    }
                    items(doneOrders.size) {
                        if (it == 0) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Text(text = "Выполненные", color = CustomOnPrimaryFixedVariant, fontFamily = unboundedBoldFont, fontSize = 20.sp)
                            }
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                            OrderItem(doneOrders[it])
                        }
                        Spacer(modifier = Modifier.size(20.dp))
                    }
                }
            }
            Resource.Status.ERROR -> {

            }
        }
    }
}