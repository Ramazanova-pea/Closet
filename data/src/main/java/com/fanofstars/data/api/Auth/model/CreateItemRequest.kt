package com.fanofstars.data.api.Auth.model

data class CreateItemRequest(
    val token: String,
    val name: String,
    val imagePath: String,
    val tags: List<String>,
    val notes: String
)
