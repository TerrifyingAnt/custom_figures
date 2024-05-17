package xd.jg.custom_figures.presentation.dialogs_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import xd.jg.custom_figures.data.dto.DialogDto
import xd.jg.custom_figures.presentation.navigation.Routes
import xd.jg.custom_figures.ui.theme.CustomOnPrimaryContainer
import xd.jg.custom_figures.ui.theme.CustomPrimaryContainer
import xd.jg.custom_figures.ui.theme.robotoRegularFont
import xd.jg.custom_figures.utils.Constants.ROUNDED

@Composable
fun DialogCard(navController: NavController, dialog: DialogDto) {
    Column(
        Modifier
            .wrapContentHeight()
            .clickable {
                navController.navigate(
                    Routes.ChatScreen.route.replace(
                        "{chat_id}",
                        dialog.dialogId.toString()
                    )
                )
            },
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.size(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(ROUNDED.dp))
                .background(
                    CustomPrimaryContainer
                )
        ) {
            Spacer(Modifier.size(5.dp))
            Box(modifier = Modifier
                .clip(CircleShape)
                .size(75.dp)) {
                Image(
                    painter = rememberAsyncImagePainter(dialog.userImage),
                    contentDescription = "users_image"
                )
            }
            Spacer(Modifier.size(5.dp))
            Column(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxHeight()) {
                Text("Чат по заказу №${dialog.orderId}", color = CustomOnPrimaryContainer, fontFamily = robotoRegularFont, fontSize = 20.sp)
                Spacer(Modifier.size(5.dp))
                val dialogText = if(dialog.lastMessage.length > 15) "${dialog.lastMessage.subSequence(0, 14)}..." else dialog.lastMessage
                Text(dialogText, color = CustomOnPrimaryContainer, fontFamily = robotoRegularFont, fontSize = 20.sp)
            }
        }
        Spacer(modifier = Modifier.size(10.dp))
    }
}