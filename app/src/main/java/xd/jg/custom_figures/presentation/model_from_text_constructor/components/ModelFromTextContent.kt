package xd.jg.custom_figures.presentation.model_from_text_constructor.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import xd.jg.custom_figures.presentation.components.CustomButton
import xd.jg.custom_figures.presentation.components.CustomTextField
import xd.jg.custom_figures.presentation.model_from_text_constructor.ModelFromTextConstructorViewModel
import xd.jg.custom_figures.ui.theme.CustomAccent
import xd.jg.custom_figures.ui.theme.CustomPrimary
import xd.jg.custom_figures.ui.theme.CustomSecondaryContainer
import xd.jg.custom_figures.ui.theme.unboundedBoldFont
import xd.jg.custom_figures.ui.theme.unboundedLightFont

@Composable
fun ModelFromTextContent(viewModel: ModelFromTextConstructorViewModel = hiltViewModel()) {
    LazyColumn(modifier = Modifier.fillMaxWidth(0.9f)) {
        item {
            CustomTextField(
                modifier = Modifier,
                description = "Название фигурки",
                hint = "Название фигурки",
                textValue = viewModel.modelFromPhotoConstructorUIState.value.figureTitle.value,
                onValueChanged = viewModel::updateTitle
            )
        }
        item {
            Text(text ="Введите название фигурки, чтобы удобно видеть ее в корзине",
                color = CustomAccent,
                fontFamily = unboundedLightFont,
                fontSize = 14.sp)
        }

        item {
                CustomTextField(
                    modifier = Modifier,
                    description = "Описание фигурки",
                    hint = "Описание фигурки",
                    textValue = viewModel.modelFromPhotoConstructorUIState.value.figureDescription.value,
                    onValueChanged = viewModel::updateDescription,
                    singleLine = false,
                )
            }
        item {
            Text(text ="Постарайтесь максимально подробно описать Вашу задумку. " +
                    "Наши мастера в дальнейшем свяжутся с Вами для уточнения деталей",
                color = CustomAccent,
                fontFamily = unboundedLightFont,
                fontSize = 14.sp)
        }
        item {
            CustomTextField(
                description = "Референсы",
                hint = "Референсы",
                textValue = viewModel.modelFromPhotoConstructorUIState.value.figureReferences.value,
                onValueChanged = viewModel::updateReferencesLink
            )
        }
        item {
            Text(text ="Вы можете указать различные ссылки,\n" +
                    "которые содержат в себе референсы \n" +
                    "того, что хотите получить: видео, \n" +
                    "фильмы, картинки",
                color = CustomAccent,
                fontFamily = unboundedLightFont,
                fontSize = 14.sp)
        }
        item {
            Divider()
        }
        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Раскрасить фигурку", color = CustomPrimary, fontFamily = unboundedBoldFont)
                Switch(
                    checked = viewModel.modelFromPhotoConstructorUIState.value.switchColorFigure.value,
                    onCheckedChange = { viewModel.updateColorFigureSwitch() })
            }
        }
        item {
            Divider()
        }
        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Подвижная фигурка", color = CustomPrimary, fontFamily = unboundedBoldFont)
                Switch(checked = false, onCheckedChange = {viewModel.updateMovableFigureSwitch()})
            }
        }
        item {
            Column(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(), verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.CenterHorizontally) {
                CustomButton(buttonColor = CustomSecondaryContainer, buttonText = "В корзину!", onClick = { viewModel.saveDescription() }, modifiers = Modifier.fillMaxWidth(0.8f))
                Spacer(Modifier.size(5.dp))
            }
        }
    }
}

@Composable
@Preview
fun ModelFromTextContentPreview() {
    ModelFromTextContent()
}