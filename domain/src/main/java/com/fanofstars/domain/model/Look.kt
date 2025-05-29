package com.fanofstars.domain.model

data class Look(
    val id_look: String,
    val name: String,
    val notes: String,
    val items: List<Item>
)
