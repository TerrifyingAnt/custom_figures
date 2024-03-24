package xd.jg.custom_figures.presentation.main_screen

import xd.jg.custom_figures.data.dto.ModelPartListDto
import xd.jg.custom_figures.presentation.main_screen.models.PartModelData

data class MainScreenUIState (
    val photoWasMade: Boolean = false,
    val photoUri: String = "",
    val modelPartsList: ModelPartListDto? = null, // тут ссылки на сервер с модельками
    val eyes: String = "", // тут абсолютные пути в андроиде до файлов с модельками
    val hair: String = "",
    val body: String = "",
    val isLoading: Boolean = false
)