package xd.jg.custom_figures.presentation.model_from_photo_constructor_screen.components

import android.content.res.AssetManager
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import xd.jg.custom_figures.presentation.model_from_photo_constructor_screen.models.PartModelData
import xd.jg.custom_figures.ui.theme.CustomAccent
import xd.jg.custom_figures.ui.theme.unboundedBoldFont
import xd.jg.custom_figures.utils.Constants.ROUNDED

@Composable
fun PartModelViewer(
        partModelData: PartModelData,
    ) {
    val showFilePickerDialog = remember { mutableStateOf(false)}
    val assetManager = LocalContext.current.assets
    Card(shape = RoundedCornerShape(5.dp), modifier = Modifier
        .size(100.dp)
        .clickable {
            showFilePickerDialog.value = true
        }) {
        Box(modifier = Modifier
            .clip(RoundedCornerShape(ROUNDED.dp))
            .background(partModelData.color)) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxHeight()
            ) {
                Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                    Text(partModelData.partTitle, fontFamily = unboundedBoldFont, color = CustomAccent, textAlign = TextAlign.Center)
                }
            }
            DownloadProgressIndicator(partModelData.state)
        }
    }
    Spacer(modifier = Modifier.size(10.dp))
    if (showFilePickerDialog.value) {
        FilePickerDialog(
            assetManager = assetManager,
            onFileSelected = { },
            onDismissRequest = { showFilePickerDialog.value = false }
        )
    }
}

@Composable
fun FilePickerDialog(
    assetManager: AssetManager,
    onFileSelected: (String) -> Unit,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text("Выберите цвет глаз") },
        text = { FilePickerList(assetManager, onFileSelected) },
        confirmButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Отмена")
            }
        }
    )
}

@Composable
fun FilePickerList(assetManager: AssetManager, onFileSelected: (String) -> Unit) {
    val files = listAssetFiles(assetManager, "eyes/")
    LazyColumn {
        items(files) { file ->
            TextButton(onClick = { onFileSelected(file) }) {
                Text(file)
            }
        }
    }
}

fun listAssetFiles(assetManager: AssetManager, path: String): List<String> {
    val files = mutableListOf<String>()
        val assets = assetManager.list(path)
        if (assets != null) {
            for (asset in assets) {
                val fullPath = if (path.isNotEmpty()) "$path/$asset" else asset
                if (fullPath.contains(".")) {
                    files.add(fullPath)
                } else {
                    files.addAll(listAssetFiles(assetManager, fullPath))
                }
            }
        }
    return files
}
