package xd.jg.custom_figures.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import xd.jg.custom_figures.presentation.main_screen.MainScreenViewModel
import xd.jg.custom_figures.presentation.main_screen.models.PartModelData

object Utils {
    @Composable
    fun createTestData(
        mainScreenViewModel: MainScreenViewModel = hiltViewModel()
    ): List<PartModelData> {
        return listOf(
            PartModelData(
                "https://i.pinimg.com/736x/f7/f9/2d/f7f92d67e7f9b3fd39c64443b7d5f61f.jpg",
                "Волосы",
                mainScreenViewModel.downloadStateHair.collectAsState()
            ),
            PartModelData(
                "https://yt3.googleusercontent.com/Zy2eVPmuXRp1LGRdkecGrk3VOFfSQiGY7JZuCGXqTayYjhyY4-_tL1FsBBPDNiEaPZ2UXKLmnBk=s900-c-k-c0x00ffffff-no-rj",
                "Глазки",
                mainScreenViewModel.downloadStateEyes.collectAsState()
            ),
            PartModelData(
                "https://i.pinimg.com/originals/19/41/f6/1941f69eb904fef2853b700fa9cd988f.jpg",
                "Тельце",
                mainScreenViewModel.downloadStateBody.collectAsState()
            )
        )
    }

    fun getColorFromAngle(angle: Float, distance: Float, maxDistance: Float = 150f): Color {
        val hue = angle / 360
        val saturation = distance / maxDistance
        return Color.hsv(hue, saturation, 1f)
    }
}