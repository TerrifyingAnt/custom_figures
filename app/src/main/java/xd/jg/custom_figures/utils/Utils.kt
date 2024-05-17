package xd.jg.custom_figures.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import xd.jg.custom_figures.data.dto.DialogDto
import xd.jg.custom_figures.data.dto.MessageDto
import xd.jg.custom_figures.presentation.model_from_photo_constructor_screen.ModelFromPhotoConstructorViewModel
import xd.jg.custom_figures.presentation.model_from_photo_constructor_screen.models.PartModelData

object Utils {
    @Composable
    fun createTestData(
        modelFromPhotoConstructorViewModel: ModelFromPhotoConstructorViewModel = hiltViewModel()
    ): List<PartModelData> {
        return listOf(
            PartModelData(
                "https://i.pinimg.com/736x/f7/f9/2d/f7f92d67e7f9b3fd39c64443b7d5f61f.jpg",
                "Волосы",
                modelFromPhotoConstructorViewModel.modelFromPhotoConstructorUIState.value.hair
            ),
            PartModelData(
                "https://yt3.googleusercontent.com/Zy2eVPmuXRp1LGRdkecGrk3VOFfSQiGY7JZuCGXqTayYjhyY4-_tL1FsBBPDNiEaPZ2UXKLmnBk=s900-c-k-c0x00ffffff-no-rj",
                "Глазки",
                modelFromPhotoConstructorViewModel.modelFromPhotoConstructorUIState.value.eyes
            ),
            PartModelData(
                "https://i.pinimg.com/originals/19/41/f6/1941f69eb904fef2853b700fa9cd988f.jpg",
                "Тельце",
                modelFromPhotoConstructorViewModel.modelFromPhotoConstructorUIState.value.body
            )
        )
    }

    fun getColorFromAngle(angle: Float, distance: Float, maxDistance: Float = 150f): Color {
        val hue = angle / 360
        val saturation = distance / maxDistance
        return Color.hsv(hue, saturation, 1f)
    }

    fun getDialogs(): List<DialogDto> {
        return listOf(
            DialogDto(
                1,
                "Удачного отдыха!",
                "https://yt3.googleusercontent.com/OvJ6KOZvmy-x7n_Iplh-Rn4L6ELkmgiQmsmQH7p3LQx-gLnl2SCt8ADKlECjVL7242y0QYfrGg=s900-c-k-c0x00ffffff-no-rj",
                "2024-05-16T14:06:00Z",
                1
            ),
            DialogDto(
                2,
                "Ghbdtn!",
                "https://yt3.googleusercontent.com/KDkQfIp6tG2DZsPzRVBuRYLKpOiEBg8zdyRVaq2u47nE33vD5151n-pvc9MUUFREk35DNvumtQ=s900-c-k-c0x00ffffff-no-rj",
                "2024-05-16T14:06:00Z",
                2
            )
        )
    }

    fun getMessagesByDialog(): List<MessageDto> {
        return listOf(
            MessageDto("Привет!", true, "2024-05-16T10:00:00Z"),
            MessageDto("Здравствуй!", false, "2024-05-16T10:01:00Z"),
            MessageDto("Как твои дела?", true, "2024-05-16T10:02:00Z"),
            MessageDto("Все супер, спасибо!", false, "2024-05-16T10:03:00Z"),
            MessageDto("А у тебя как?", false, "2024-05-16T10:04:00Z"),
            MessageDto("Тоже отлично!", true, "2024-05-16T10:05:00Z"),
            MessageDto("Чем занимаешься?", false, "2024-05-16T10:06:00Z"),
            MessageDto("Работаю над новым проектом", true, "2024-05-16T10:07:00Z"),
            MessageDto("Круто, расскажи подробнее", false, "2024-05-16T10:08:00Z"),
            MessageDto(
                "Это приложение для планирования задач. Пока на начальной стадии разработки",
                true,
                "2024-05-16T10:09:00Z"
            ),
            MessageDto("Здорово, удачи тебе с этим!", false, "2024-05-16T10:10:00Z"),
            MessageDto("Спасибо!", true, "2024-05-16T10:11:00Z"),
            MessageDto("А что нового у тебя?", true, "2024-05-16T10:12:00Z"),
            MessageDto("Да ничего особенного, обычные дела", false, "2024-05-16T10:13:00Z"),
            MessageDto("Понятно", true, "2024-05-16T10:14:00Z"),
            MessageDto(
                "Кстати, ты смотрел новый сериал на Netflix?",
                false,
                "2024-05-16T10:15:00Z"
            ),
            MessageDto("Нет еще, стоит глянуть?", true, "2024-05-16T10:16:00Z"),
            MessageDto("Да, очень интересный сюжет!", false, "2024-05-16T10:17:00Z"),
            MessageDto("Хорошо, добавлю в свой список", true, "2024-05-16T10:18:00Z"),
            MessageDto("Отлично!", false, "2024-05-16T10:19:00Z"),
            MessageDto("Ладно, мне пора бежать на встречу", true, "2024-05-16T10:20:00Z"),
            MessageDto("Окей, до связи!", false, "2024-05-16T10:21:00Z"),
            MessageDto("Пока!", true, "2024-05-16T10:22:00Z"),
            MessageDto("Привет, как прошла твоя встреча?", false, "2024-05-16T14:00:00Z"),
            MessageDto("Все прошло отлично, спасибо", true, "2024-05-16T14:01:00Z"),
            MessageDto("Рад слышать!", false, "2024-05-16T14:02:00Z"),
            MessageDto("Да, продуктивно поработали", true, "2024-05-16T14:03:00Z"),
            MessageDto("Это хорошо", false, "2024-05-16T14:04:00Z"),
            MessageDto("Ага, теперь можно немного расслабиться", true, "2024-05-16T14:05:00Z"),
            MessageDto("Удачного отдыха!", false, "2024-05-16T14:06:00Z")
        )
    }
}