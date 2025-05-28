package com.fanofstars.data.api.Auth.model

data class ItemResponse(
    val id_item: String,
    val name: String,
    val notes: String?,
    val imagePath: String,
    val tags: List<String>
)
