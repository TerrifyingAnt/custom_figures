package xd.jg.custom_figures.presentation.main_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import xd.jg.custom_figures.presentation.main_screen.models.PartModelData

@OptIn(ExperimentalCoilApi::class)
@Composable
fun PartModelViewer(partModelData: PartModelData) {
    Spacer(modifier = Modifier.size(10.dp))
    Card(shape = RoundedCornerShape(5.dp), modifier = Modifier.size(100.dp)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxHeight(0.85f)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = rememberImagePainter(partModelData.imageSource),
                    contentDescription = partModelData.partTitle,
                    Modifier.fillMaxWidth()
                )
            }
        }
        Text(
            textAlign = TextAlign.Center,
            text = partModelData.partTitle,
            modifier = Modifier.fillMaxWidth()
        )


    }
}
