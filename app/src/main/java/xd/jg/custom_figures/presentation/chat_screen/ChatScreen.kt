package xd.jg.custom_figures.presentation.chat_screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardBackspace
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import xd.jg.custom_figures.data.dto.MessageDto
import xd.jg.custom_figures.ui.theme.CustomOnPrimaryContainer
import xd.jg.custom_figures.ui.theme.CustomPrimary
import xd.jg.custom_figures.ui.theme.CustomPrimaryContainer
import xd.jg.custom_figures.ui.theme.CustomSecondary
import xd.jg.custom_figures.ui.theme.robotoRegularFont
import xd.jg.custom_figures.ui.theme.unboundedBoldFont
import xd.jg.custom_figures.utils.Constants.ROUNDED

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(navController: NavController, chatId: Int, viewModel: ChatViewModel = hiltViewModel()) {

    viewModel.getMessages(chatId)
    val chatListState = rememberLazyListState()
    val messages = remember {
        mutableStateListOf(
            MessageDto("Привет!", true, "2024-05-16T10:00:00Z"),
            MessageDto("Здравствуй!", false, "2024-05-16T10:01:00Z"),
            MessageDto("Как твои дела?", true, "2024-05-16T10:02:00Z"),
            MessageDto("Все супер, спасибо!", false, "2024-05-16T10:03:00Z"),
            MessageDto("А у тебя как?", false, "2024-05-16T10:04:00Z"),
        )
    }

    LaunchedEffect(Unit){
        chatListState.animateScrollToItem(viewModel.chatUIState.value.messages.size)
    }

    BackHandler {
        navController.popBackStack()
    }
    Column(modifier = Modifier.fillMaxHeight(0.925f), horizontalAlignment = Alignment.CenterHorizontally) {
        TopAppBar(
            title = {
                Text(
                    "Заказ $chatId",
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
        LazyColumn(modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(0.9f), horizontalAlignment = Alignment.CenterHorizontally,
            state = chatListState) {
            items(messages) {
                if (it.fromMe) {
                    Row(modifier = Modifier.fillMaxWidth()
                        , horizontalArrangement = Arrangement.End) {
                        Surface(shape = RoundedCornerShape(
                            ROUNDED.dp,
                            ROUNDED.dp,
                            0.dp,
                            ROUNDED.dp
                        ), color = CustomPrimaryContainer,
                            modifier = Modifier.wrapContentSize()) {
                            Column() {
                                Spacer(modifier = Modifier.size(5.dp))
                                Row(){
                                    Spacer(modifier = Modifier.size(5.dp))
                                    Text(
                                        it.messageData,
                                        fontFamily = robotoRegularFont,
                                        color = CustomOnPrimaryContainer,
                                        fontSize = 18.sp
                                    )
                                    Spacer(modifier = Modifier.size(5.dp))
                                }
                                Spacer(modifier = Modifier.size(5.dp))
                            }
                        }
                    }
                Spacer(modifier = Modifier.size(5.dp))
                }
                else {
                    Row(modifier = Modifier.fillMaxWidth()
                        , horizontalArrangement = Arrangement.Start) {
                        Surface(shape = RoundedCornerShape(
                            ROUNDED.dp,
                            ROUNDED.dp,
                            ROUNDED.dp,
                            0.dp
                        ), color = CustomPrimaryContainer,
                        modifier = Modifier.wrapContentSize()) {
                            Column() {
                                Spacer(modifier = Modifier.size(5.dp))
                                Row(){
                                    Spacer(modifier = Modifier.size(5.dp))
                                    Text(
                                        it.messageData,
                                        fontFamily = robotoRegularFont,
                                        color = CustomOnPrimaryContainer,
                                        fontSize = 18.sp
                                    )
                                    Spacer(modifier = Modifier.size(5.dp))
                                }
                                Spacer(modifier = Modifier.size(5.dp))
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.size(5.dp))
            }
        }
    }
    Column(Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Bottom) {
        Surface(color = CustomPrimary, modifier = Modifier
            .fillMaxHeight(0.075f)
            .fillMaxWidth()) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(), verticalAlignment = Alignment.CenterVertically) {
                Spacer(modifier = Modifier.size(10.dp))
                TextField(
                    value = viewModel.chatUIState.value.currentMessage.value,
                    onValueChange = viewModel::updateMessage,
                    placeholder = { Text(text = "Сообщение...", fontFamily = unboundedBoldFont, color = Color.White, fontSize = 18.sp) },
                    leadingIcon = { Icon(imageVector = Icons.Default.AttachFile, contentDescription = null, tint = Color.White) },
                    trailingIcon = { Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = null, tint = Color.White, modifier = Modifier.clickable {
                        messages.add(MessageDto(viewModel.chatUIState.value.currentMessage.value, true, "xd"))
                        viewModel.sendMessage()
                    }) },
                    colors =  TextFieldDefaults.colors(
                        focusedContainerColor = CustomPrimary,
                        unfocusedContainerColor = CustomPrimary,
                        cursorColor = CustomOnPrimaryContainer
                    ),
                    textStyle = TextStyle(color= Color.White, fontFamily = robotoRegularFont, fontSize=14.sp),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}