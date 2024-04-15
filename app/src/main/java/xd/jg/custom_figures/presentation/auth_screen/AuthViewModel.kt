package xd.jg.custom_figures.presentation.auth_screen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import xd.jg.custom_figures.data.local.DataStoreManager
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
): ViewModel() {

}