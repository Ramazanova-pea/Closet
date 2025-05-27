package ru.fan_of_stars.closet.ui.closet.addItem

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fanofstars.domain.usecases.GetTagsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent.inject
import java.io.File
import java.io.FileOutputStream

class AddItemViewModel(
    private val getTagsUseCase: GetTagsUseCase,
    application: Application
) : ViewModel() {
    //private val context = getApplication<Application>().applicationContext

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

    //fun onImageSelected(uri: Uri) {
      //  viewModelScope.launch {
        //    val filePath = saveImageToInternalStorage(uri)
          //  if (filePath != null) {
            //    // Отправь ссылку на сервер
              //  uploadImagePathToServer(filePath)
           // }
       // }
    //}

    //private suspend fun saveImageToInternalStorage(uri: Uri): String? {
//        return withContext(Dispatchers.IO) {
//            try {
//                val inputStream = context.contentResolver.openInputStream(uri)
//                val fileName = "img_${System.currentTimeMillis()}.jpg"
//                val file = File(context.filesDir, fileName)
//                val outputStream = FileOutputStream(file)
//                inputStream?.copyTo(outputStream)
//                inputStream?.close()
//                outputStream.close()
//                file.absolutePath
    //           } catch (e: Exception) {
    //               e.printStackTrace()
//                null
//            }
//        }
//    }
}
