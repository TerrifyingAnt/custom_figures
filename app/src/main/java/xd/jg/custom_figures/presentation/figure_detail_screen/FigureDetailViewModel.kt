package xd.jg.custom_figures.presentation.figure_detail_screen

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import xd.jg.custom_figures.domain.remote.IFigureRepository
import xd.jg.custom_figures.utils.Resource
import xd.jg.custom_figures.work_manager.GlbDownloadWorker
import javax.inject.Inject

@HiltViewModel
class FigureDetailViewModel @Inject constructor (
    private val iFigureRepository: IFigureRepository
): ViewModel() {


    private val _figureDetailUIState = mutableStateOf(FigureDetailUIState())
    val figureDetailUIState: State<FigureDetailUIState> = _figureDetailUIState

    private fun updateUIState(update: FigureDetailUIState.() -> FigureDetailUIState) {
        _figureDetailUIState.value = _figureDetailUIState.value.update()
    }

    /** Метод получает информацию о фигурке по ее id*/
    fun getFigureInfo(figureId: Int, context: Context) = viewModelScope.launch {
        val response = iFigureRepository.getFigureById(figureId)
        if (response.data != null) {
            updateUIState {
                copy(
                    figureDetails = Resource.success(response.data)
                )
            }
            Log.d("CRINGE_LINK", response.data.modelLink)
            downloadModel(context, response.data.modelLink)
        }
        else {
            updateUIState {
                copy(
                    figureDetails = Resource.error(response.message?: "")
                )
            }
        }
    }

    fun updateClearSceneState(state: Boolean) {
        updateUIState {
            copy(
                clearScene = mutableStateOf(state)
            )
        }
    }
    fun updateModelState() {
        updateUIState {
            copy(
                figureModel = Resource.loading()
            )
        }
    }

    /** метод для скачивания модели*/
    private fun downloadModel(applicationContext: Context, fileUrl: String) {
        val fileName = "figure"
        val data = workDataOf(
            "fileUrl" to fileUrl,
            "fileName" to fileName
        )
        val workRequest = OneTimeWorkRequestBuilder<GlbDownloadWorker>()
            .setInputData(data)
            .build()

        WorkManager.getInstance(applicationContext).enqueue(workRequest)
        val workRequestId = GlbDownloadWorker.enqueueDownload(fileUrl, fileName, applicationContext)
        val workInfoFlow = WorkManager.getInstance(applicationContext).getWorkInfoByIdLiveData(workRequestId)
            .asFlow()
            .map { workInfo ->
                if (workInfo != null && workInfo.state == WorkInfo.State.SUCCEEDED) {
                    val outputData = workInfo.outputData
                    val filePath = outputData.getString("filePath")
                    val errorMessage = outputData.getString("errorMessage")
                    if (filePath != null) {
                        updateUIState {
                            copy(
                                figureModel = Resource.success(filePath)
                            )
                        }
                        Resource.success(filePath)
                    }
                    else if (errorMessage != null) {
                        Log.d("ЛОХ ЕБАТЬ", "$errorMessage")
                        Resource.error(errorMessage)
                    } else {
                        Log.d("ЛОХ ЕБАТЬ", "xd")
                        Resource.error("Unknown error")
                    }
                } else if (workInfo != null && workInfo.state == WorkInfo.State.RUNNING) {
                    val progress = workInfo.progress.getFloat("progress", 0f)
                    updateUIState {
                        copy(
                            figureModel = Resource.loading(progress.toString())
                        )
                    }
                    Resource.loading(progress.toString())
                } else {
                    Log.d("ЛОХ ЕБАТЬ", "xdxd $fileUrl " + workInfo.toString())
                    Resource.error("xd")
                }
            }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), _figureDetailUIState.value.figureModel)



        viewModelScope.launch {
            workInfoFlow.collect {state ->
                _figureDetailUIState.value.figureModel = state
            }
        }
    }
}