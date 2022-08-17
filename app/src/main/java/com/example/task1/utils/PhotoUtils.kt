package com.example.task1.utils

import com.example.task1.R
import com.example.task1.databinding.ItemCoinBinding

class PhotoUtils() {
    fun photoSelector(id: String, bind: ItemCoinBinding) {
        when (id) {
            "btc-bitcoin" -> bind.imageView.setImageResource(R.mipmap.bitcoin)
            "eth-ethereum" -> bind.imageView.setImageResource(R.mipmap.eth)
            "usdt-tether" -> bind.imageView.setImageResource(R.mipmap.newusddd)
            "avax-avalanche" -> bind.imageView.setImageResource(R.mipmap.avax)
            "doge-dogecoin" -> bind.imageView.setImageResource(R.mipmap.doge)
            "dot-polkadot" -> bind.imageView.setImageResource(R.mipmap.dot)
            "okb-okb" -> bind.imageView.setImageResource(R.mipmap.okb3)
            "qnt-quant" -> bind.imageView.setImageResource(R.mipmap.quant)
            "rune-thorchain" -> bind.imageView.setImageResource(R.mipmap.thoree)
            "tusd-trueusd" -> bind.imageView.setImageResource(R.mipmap.trueusd)
        }
    }
}