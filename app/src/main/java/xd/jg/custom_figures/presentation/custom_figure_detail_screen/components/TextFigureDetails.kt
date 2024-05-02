package xd.jg.custom_figures.presentation.custom_figure_detail_screen.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardBackspace
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import xd.jg.custom_figures.R
import xd.jg.custom_figures.data.local.entity.BasketItemEntity
import xd.jg.custom_figures.presentation.navigation.BottomNavigationItems
import xd.jg.custom_figures.ui.theme.CustomPrimary
import xd.jg.custom_figures.ui.theme.CustomSecondary
import xd.jg.custom_figures.ui.theme.unboundedBoldFont
import xd.jg.custom_figures.utils.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFigureDetails(navController: NavController, basketItemEntity: BasketItemEntity) {

    BackHandler {
        navController.popBackStack(BottomNavigationItems.BasketScreen.route, false)
    }

    Column(modifier = Modifier.fillMaxHeight()) {
        TopAppBar(
            title = { Text(stringResource(R.string.figure_string), fontFamily = unboundedBoldFont, color = CustomSecondary) },
            navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack(BottomNavigationItems.BasketScreen.route, false)
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
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = CustomPrimary,
                ),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp)
                    .border(1.dp, CustomPrimary, RoundedCornerShape(Constants.ROUNDED.dp)),
                shape = RoundedCornerShape(Constants.ROUNDED.dp)
            ) {
                LazyColumn(modifier = Modifier.fillMaxWidth(0.9f), horizontalAlignment = Alignment.CenterHorizontally) {
                    item {
                        Text(
                            text = "Название: ${basketItemEntity.title}",
                            color = Color.White,
                            fontFamily = unboundedBoldFont,
                            fontSize = 26.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                    item {
                        Divider()
                    }
                    item {
                        Text(
                            text = "Описание: ${basketItemEntity.description}",
                            color = Color.White,
                            fontFamily = unboundedBoldFont,
                            fontSize = 26.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                    item {
                        Divider()
                    }
                    item {
                        Text(
                            text = "Референсы: ${basketItemEntity.references}",
                            color = Color.White,
                            fontFamily = unboundedBoldFont,
                            fontSize = 26.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                    item {
                        Divider()
                    }
                    item {
                        Text(
                            text = "Цветная: ${if (basketItemEntity.colored) "Да" else "Нет"}",
                            color = Color.White,
                            fontFamily = unboundedBoldFont,
                            fontSize = 26.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                    item {
                        Divider()
                    }
                    item {
                        Text(
                            text = "Подвижная: ${if (basketItemEntity.movable) "Да" else "Нет"}",
                            color = Color.White,
                            fontFamily = unboundedBoldFont,
                            fontSize = 26.sp,
                            textAlign = TextAlign.Center)
                    }
                }
            }
        }
    }
}