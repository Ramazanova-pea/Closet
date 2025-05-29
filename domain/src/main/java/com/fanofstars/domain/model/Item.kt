package com.fanofstars.domain.model

data class Item(
    val token: String,
    val idItem: String?,
    val name: String,
    val imagePath: String,
    val tags: List<String>,
    val notes: String
)
