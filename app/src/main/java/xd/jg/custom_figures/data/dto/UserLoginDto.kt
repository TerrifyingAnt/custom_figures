package xd.jg.custom_figures.data.dto

import com.fasterxml.jackson.annotation.JsonProperty
import xd.jg.custom_figures.utils.Constants.CLIENT_ID
import xd.jg.custom_figures.utils.Constants.CLIENT_SECRET
import xd.jg.custom_figures.utils.Constants.GRANT_TYPE

data class UserLoginDto (
    val username: String,
    val password: String,
    @JsonProperty("client_id")
    val clientId: String = CLIENT_ID,
    @JsonProperty("client_secret")
    val clientSecret: String = CLIENT_SECRET,
    @JsonProperty("grant_type")
    val grantType: String = GRANT_TYPE
)