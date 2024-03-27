package xd.jg.custom_figures.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
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
        private val BODY_PATH = stringPreferencesKey("body_path")
        private val HAIR_PATH = intPreferencesKey("hair_path")
        private val EYES_PATH = stringPreferencesKey("eyes_path")
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


}
