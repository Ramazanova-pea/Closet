package ru.fan_of_stars.closet.ui.closet.addItem

import android.app.Application
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fanofstars.domain.model.Item
import com.fanofstars.domain.usecases.CreateItemUseCase
import com.fanofstars.domain.usecases.GetTagsUseCase
import com.fanofstars.domain.usecases.UploadImagePathUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent.inject
import java.io.File
import java.io.FileOutputStream

class AddItemViewModel(
    private val getTagsUseCase: GetTagsUseCase,
    private val uploadImagePathUseCase: UploadImagePathUseCase,
    private val createItemUseCase: CreateItemUseCase,
    private val application: Application
) : AndroidViewModel(application) {
    val allTags = mutableStateListOf<String>()
    val selectedTags = mutableStateListOf<String>()
    val name = mutableStateOf("")
    val notes = mutableStateOf("")
    var imagePath: String? = null // путь к загруженному изображению
    var selectedImageUri by mutableStateOf<Uri?>(null)
        private set
    private val _itemSavedEvent = MutableSharedFlow<Unit>()
    val itemSavedEvent = _itemSavedEvent.asSharedFlow()

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
            // Сохраняем Uri для отображения фото
            selectedImageUri = uri

            // Сохраняем фото во внутреннее хранилище и путь к нему
            val filePath = saveImageToInternalStorage(application.applicationContext, uri)
            if (filePath != null) {
                imagePath = filePath
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

    fun onSaveItem() {
        viewModelScope.launch {
            try {
                if (imagePath == null) {
                    Toast.makeText(getApplication(), "Image path is null", Toast.LENGTH_SHORT).show()
                    return@launch
                }

                val token = getApplication<Application>()
                    .getSharedPreferences("auth", Context.MODE_PRIVATE)
                    .getString("token", "") ?: ""

                val item = Item(
                    token = token,
                    name = name.value,
                    imagePath = imagePath!!,
                    tags = selectedTags.toList(),
                    notes = notes.value,
                    idItem = null
                )

                createItemUseCase(item)
                Toast.makeText(getApplication(), "Item saved", Toast.LENGTH_SHORT).show()

                // Отправляем сигнал в UI, что запись сохранена
                _itemSavedEvent.emit(Unit)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(getApplication(), "Error: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }


}
