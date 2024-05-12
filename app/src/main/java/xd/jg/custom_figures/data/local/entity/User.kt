package xd.jg.custom_figures.data.local.entity

data class User(
    var id: Int? = null,
    var name: String? = null,
    var login: String? = null,
    var password: String? = null,
    var phoneNumber: String? = null,
    var avatarSourcePath: String? = null,
    var type: String? = null,
)
