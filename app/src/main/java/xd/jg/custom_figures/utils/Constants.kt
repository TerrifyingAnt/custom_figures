package xd.jg.custom_figures.utils

import java.util.regex.Pattern

object Constants {
    /** ссылки */
    const val BASE_URL = "http://levandrovskiy.ru:8777/"
    const val BASE_MAIN_URL = "http://10.147.17.181:8090/"

    /** названия шардов*/
    const val USER_DATASTORE = "USER_DATASTORE"
    const val TOKEN_DATASTORE = "TOKEN_DATASTORE"

    /** авторизация через кейклок*/
    const val AUTH_LINK = "http://localhost:8081/realms/users/protocol/openid-connect/token"
    const val CLIENT_SECRET = "pzbwVDf221bh14QmIVlSVahp9pc53Wbg"

    /** ui`ные константы */
    const val ROUNDED = 16
    const val START_PADDING = 20
    const val END_PADDING = 20

    val EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    const val BASE_AVATAR = "https://static.vecteezy.com/system/resources/previews/009/292/244/large_2x/default-avatar-icon-of-social-media-user-vector.jpg"

    const val CLIENT_ID = "auth_service"

    const val GRANT_TYPE = "password"
}