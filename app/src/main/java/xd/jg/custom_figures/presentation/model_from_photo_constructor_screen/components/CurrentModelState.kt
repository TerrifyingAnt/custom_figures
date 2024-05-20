package xd.jg.custom_figures.presentation.model_from_photo_constructor_screen.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import xd.jg.custom_figures.presentation.model_from_photo_constructor_screen.models.PartModelData

@Composable
fun CurrentModelState(currentModelState: List<PartModelData>) {
    LazyRow(modifier = Modifier.fillMaxWidth()) {
        item {
            Spacer(modifier = Modifier.size(10.dp))
        }
        items(currentModelState) {currentPart ->
            PartModelViewer(currentPart)
        }
    }
    Spacer(modifier = Modifier.size(10.dp))
}