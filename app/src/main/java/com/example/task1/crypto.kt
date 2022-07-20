package com.example.task1

data class crypto(
    val id: String,
    val name: String,
    val symbol: String,
    val rank: Int,
    val is_new: Boolean,
    val is_active: Boolean,
    val type: String
) {
    override fun toString(): String {
        return "crypto(id='$id', name='$name', symbol='$symbol', rank=$rank, is_new=$is_new, is_active=$is_active, type='$type')"
    }
}

