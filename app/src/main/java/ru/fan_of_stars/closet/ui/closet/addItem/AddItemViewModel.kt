package ru.fan_of_stars.closet.ui.closet.addItem

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fanofstars.domain.usecases.GetTagsUseCase
import com.fanofstars.domain.usecases.UploadImagePathUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent.inject
import java.io.File
import java.io.FileOutputStream

class AddItemViewModel(
    private val getTagsUseCase: GetTagsUseCase,
    private val uploadImagePathUseCase: UploadImagePathUseCase,
    private val application: Application
) : ViewModel() {
    val allTags = mutableStateListOf<String>()
    val selectedTags = mutableStateListOf<String>()

    init {
        loadTags()
    }

    private fun loadTags() {
        viewModelScope.launch {
            try {
                val tags = getTagsUseCase()
                allTags.clear()
                allTags.addAll(tags)
            } catch (e: Exception) {
                // Обработка ошибки (например, показать ошибку в UI)
                e.printStackTrace()
            }
        }
    }

    fun toggleTag(tag: String) {
        if (selectedTags.contains(tag)) {
            selectedTags.remove(tag)
        } else {
            selectedTags.add(tag)
        }
    }

    private suspend fun uploadImagePathToServer(path: String) {
        try {
            uploadImagePathUseCase(path)
        } catch (e: Exception) {
            e.printStackTrace()
            // Здесь можно отправить событие для UI, если нужно
        }
    }


    fun onImageSelected(uri: Uri) {
        viewModelScope.launch {
            val filePath = saveImageToInternalStorage(application.applicationContext, uri)
            if (filePath != null) {
                uploadImagePathToServer(filePath)
            }
        }
    }

    private suspend fun saveImageToInternalStorage(context: Context, uri: Uri): String? {
        return withContext(Dispatchers.IO) {
            try {
                val inputStream = context.contentResolver.openInputStream(uri)
                val fileName = "img_${System.currentTimeMillis()}.jpg"
                val file = File(context.filesDir, fileName)
                val outputStream = FileOutputStream(file)
                inputStream?.copyTo(outputStream)
                inputStream?.close()
                outputStream.close()
                file.absolutePath // вернём путь
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

}
