package com.fanofstars.data.api.Auth

import android.util.Log
import com.fanofstars.data.api.Auth.model.CreateLookRequest
import com.fanofstars.domain.model.Look
import com.fanofstars.domain.repositories.LookRepository

class LookRepositoryImpl(
    private val apiService: AllApi // интерфейс Retrofit
) : LookRepository {

    override suspend fun createLook(name: String, notes: String?, items: List<String>): Result<String> {
        return try {
            val response = apiService.createLook(
                CreateLookRequest(
                    name = name,
                    notes = notes,
                    items = items
                )
            )
            if (response.isSuccessful) {
                val idLook = response.body()?.get("id_look")
                if (idLook != null) {
                    Result.success(idLook)
                } else {
                    Result.failure(Exception("Missing id_look"))
                }
            } else {
                Result.failure(Exception("Error: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getLooks(token: String): List<Look> {
        Log.d("Repository", "getLooks: Запрос образов с токеном: $token")

        val requestMap = mapOf("token" to token)
        val response = try {
            apiService.getLooks(requestMap).also {
                Log.d("Repository", "getLooks: Ответ от API получен, кол-во образов: ${it.size}")
            }
        } catch (e: Exception) {
            Log.e("Repository", "getLooks: Ошибка при запросе образов: ${e.localizedMessage}", e)
            throw e // пробросим дальше, если требуется
        }

        val mappedLooks = response.map { lookResponse ->
            Look(
                id_look = lookResponse.id_look,
                name = lookResponse.name,
                notes = lookResponse.notes ?: "",
                items = lookResponse.items.map { item ->
                    com.fanofstars.domain.model.Item(
                        idItem = item.idItem,
                        name = item.name,
                        notes = item.notes ?: "",
                        imagePath = item.imagePath,
                        token = "",
                        tags = emptyList()
                    )
                }
            )
        }

        Log.d("Repository", "getLooks: Преобразованные образы: ${mappedLooks.size}")

        return mappedLooks
    }
}
