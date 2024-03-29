package xd.jg.custom_figures.data.local

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.alpha
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import xd.jg.custom_figures.utils.Constants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreManager @Inject constructor(
    @ApplicationContext private val appContext: Context
) {
    companion object {
        private val Context.dataStore by preferencesDataStore(Constants.USER_DATASTORE)
        private val PHOTO_WAS_MADE = booleanPreferencesKey("photo_was_made")
        private val SKYBOX_COLOR_R = intPreferencesKey("skybox_color_r")
        private val SKYBOX_COLOR_G = intPreferencesKey("skybox_color_g")
        private val SKYBOX_COLOR_B = intPreferencesKey("skybox_color_b")
        private val SKYBOX_COLOR_A = intPreferencesKey("skybox_color_a")

    }

    suspend fun setPhotoWasMade(photoWasMade: Boolean) {
        appContext.dataStore.edit { localStorage ->
            localStorage[PHOTO_WAS_MADE] = photoWasMade
        }
    }

    fun getPhotoWasMade(): Flow<Boolean?> {
        return appContext.dataStore.data.map { localStorage ->
            localStorage[PHOTO_WAS_MADE]
        }
    }

    suspend fun setCurrentColor(color: Color) {
        appContext.dataStore.edit { localStorage ->
            localStorage[SKYBOX_COLOR_A] = color.toArgb().alpha
            localStorage[SKYBOX_COLOR_B] = color.toArgb().blue
            localStorage[SKYBOX_COLOR_G] = color.toArgb().green
            localStorage[SKYBOX_COLOR_R] = color.toArgb().red
        }
    }

    fun getCurrentColor(): Flow<Color> {
        return appContext.dataStore.data.map {localStorage ->
            Color(localStorage[SKYBOX_COLOR_R] ?: 0, localStorage[SKYBOX_COLOR_G] ?: 0, localStorage[SKYBOX_COLOR_B] ?: 0, localStorage[SKYBOX_COLOR_A] ?: 0)
        }
    }


}
