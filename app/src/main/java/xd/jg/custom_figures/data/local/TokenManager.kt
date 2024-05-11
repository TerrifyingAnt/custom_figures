package xd.jg.custom_figures.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import xd.jg.custom_figures.utils.Constants.TOKEN_DATASTORE
import javax.inject.Inject
import javax.inject.Singleton
@Singleton
class TokenManager @Inject constructor(
    @ApplicationContext private val appContext: Context
) {
    companion object {
        private val Context.dataStore by preferencesDataStore(TOKEN_DATASTORE)
        private val ACCESS_TOKEN = stringPreferencesKey("access_token")
        private val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        private val ACCESS_EXPIRES_IN = stringPreferencesKey("access_expires_in")
        private val login = stringPreferencesKey("login")
    }

    suspend fun setAccessToken(accessToken: String) {
        appContext.dataStore.edit { tokenStore ->
            tokenStore[ACCESS_TOKEN] = accessToken
        }
    }

    fun getAccessToken(): Flow<String?> {
        return appContext.dataStore.data.map { tokenStore ->
            tokenStore[ACCESS_TOKEN]
        }
    }

    suspend fun setRefreshToken(refreshToken: String) {
        appContext.dataStore.edit { tokenStore ->
            tokenStore[REFRESH_TOKEN] = refreshToken
        }
    }

    fun getRefreshToken(): Flow<String?> {
        return appContext.dataStore.data.map { tokenStore ->
            tokenStore[REFRESH_TOKEN]
        }
    }

    suspend fun setExpiresIn(expiresIn: Int?) {
        appContext.dataStore.edit { tokenStore ->
            tokenStore[ACCESS_EXPIRES_IN] = expiresIn.toString()
        }
    }

    fun getExpiresIn(): Flow<String?> {
        return appContext.dataStore.data.map { tokenStore ->
            tokenStore[ACCESS_EXPIRES_IN]
        }
    }

}