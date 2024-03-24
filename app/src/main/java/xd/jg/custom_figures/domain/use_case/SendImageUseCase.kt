package xd.jg.custom_figures.domain.use_case

import android.util.Log
import xd.jg.custom_figures.domain.remote.IFigureRepository
import java.io.File
import javax.inject.Inject

class SendImageUseCase @Inject constructor(
    private val iFigureRepository: IFigureRepository
){
    suspend fun sendPhoto(file: File) {
        iFigureRepository.sendImage(file)
    }
}