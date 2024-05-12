package xd.jg.custom_figures.data.model

enum class OrderStatus {
    ACCEPT,
    IN_PROGRESS,
    DONE;
    companion object {
        fun fromEnum(value: String): String {
            return when (value) {
                "ACCEPT" -> "Приянт"
                "IN_PROGRESS" -> "В работе"
                "DONE" -> "Завершен"
                else -> {""}
            }
        }
    }
}