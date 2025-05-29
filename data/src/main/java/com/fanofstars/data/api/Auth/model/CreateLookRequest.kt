package com.fanofstars.data.api.Auth.model

data class CreateLookRequest(
    val name: String,
    val notes: String?,
    val items: List<String>
)

data class LookResponse(
    val id_look: String,
    val name: String,
    val notes: String?,
    val items: List<LookItemResponse>
)

data class LookItemResponse(
    val id_item: String,
    val name: String,
    val notes: String?,
    val imagePath: String
)
