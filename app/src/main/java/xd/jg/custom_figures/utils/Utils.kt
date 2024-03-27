package xd.jg.custom_figures.utils

import xd.jg.custom_figures.presentation.main_screen.models.PartModelData

object Utils {
    fun createTestData(): List<PartModelData> {
        return listOf(
            PartModelData(
                "https://i.pinimg.com/736x/f7/f9/2d/f7f92d67e7f9b3fd39c64443b7d5f61f.jpg",
                "Волосы",
                { Unit }
            ),
            PartModelData(
                "https://yt3.googleusercontent.com/Zy2eVPmuXRp1LGRdkecGrk3VOFfSQiGY7JZuCGXqTayYjhyY4-_tL1FsBBPDNiEaPZ2UXKLmnBk=s900-c-k-c0x00ffffff-no-rj",
                "Глазки",
                { Unit }
            )
        )
    }
}