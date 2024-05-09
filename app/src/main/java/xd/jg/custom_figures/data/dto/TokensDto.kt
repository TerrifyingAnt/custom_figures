package xd.jg.custom_figures.data.dto

import com.google.gson.annotations.SerializedName

data class TokensDto (
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("refresh_token")
    val refreshToken: String,
    @SerializedName("expires_in")
    val expiresIn: Int?    = null,
    @SerializedName("refresh_expires_in" )
    val refreshExpiresIn: Int? = null,
    @SerializedName("token_type")
    val tokenType: String? = null,
    @SerializedName("not-before-policy")
    val notBeforePolicy : Int? = null,
    @SerializedName("session_state")
    val sessionState: String? = null,
    @SerializedName("scope")
    val scope: String? = null

)