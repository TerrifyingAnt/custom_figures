package xd.jg.custom_figures.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BasketItemEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val type: Int,
    val figureId: Int = 0,
    val description: String = "",
    val references: String = "",
    val movable: Boolean = false,
    val colored: Boolean = false,
    val hairLink: String = "",
    val eyeLink: String = "",
    val bodyLink: String = "",
    var count: Int = 0,
    val price: Float = 0f,
    val title: String = ""
)