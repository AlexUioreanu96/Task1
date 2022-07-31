package com.example.task1.models

data class CoinModel(
    val id: String = "",
    val name: String = "",
    val symbol: String = "",
    val rank: Int = 0,
    val is_new: Boolean = false,
    val is_active: Boolean = false,
    val type: String = "",
    val contract: String = "",
    val platform: String = "",
)

