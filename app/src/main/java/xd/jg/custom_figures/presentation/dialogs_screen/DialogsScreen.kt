package xd.jg.custom_figures.presentation.dialogs_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import xd.jg.custom_figures.R
import xd.jg.custom_figures.presentation.dialogs_screen.components.DialogCard
import xd.jg.custom_figures.ui.theme.CustomSecondary
import xd.jg.custom_figures.ui.theme.unboundedBoldFont
import xd.jg.custom_figures.utils.Resource

@Composable
fun DialogsScreen(navController: NavController, viewModel: DialogsViewModel = hiltViewModel()) {
    LaunchedEffect(key1 = Unit) {
        viewModel.getDialogs()
    }
    Column(modifier = Modifier.fillMaxHeight(), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Text(
                stringResource(R.string.dialogs_string),
                fontFamily = unboundedBoldFont,
                fontSize = 30.sp,
                color = CustomSecondary
            )
        }
        Spacer(Modifier.size(20.dp))
        when(viewModel.dialogsUIState.value.dialogsList.status) {
            Resource.Status.SUCCESS -> {
                val dialogList = viewModel.dialogsUIState.value.dialogsList.data ?: return@Column
                LazyColumn(Modifier.fillMaxWidth(0.9f), horizontalAlignment = Alignment.CenterHorizontally) {
                    items(dialogList.size) {
                        DialogCard(navController, dialogList[it])
                    }
                }
            }
            Resource.Status.LOADING -> {}
            Resource.Status.ERROR -> {}
        }
    }

}