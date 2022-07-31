package com.example.task1.adapter

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.task1.CoinDetailsActivity
import com.example.task1.R
import com.example.task1.databinding.ItemCoinBinding
import com.example.task1.models.CoinModel

const val ID = "ID"

class CoinAdapter(var coinList: List<CoinModel>) :
    RecyclerView.Adapter<CoinAdapter.CoinViewHolder>() {


    data class CoinViewHolder(val bind: ItemCoinBinding) : RecyclerView.ViewHolder(bind.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        return CoinViewHolder(
            ItemCoinBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        val coin: CoinModel = coinList[position]
        holder.bind.apply {
            photoSelector(coin.id, holder.bind)
            cName.text = coin.name
            cSymbol.text = coin.symbol
            cRank.text = coin.rank.toString()
            cName.text = coin.name
            cType.text = coin.type
            if (coin.is_new) cIsNew.setTextColor(Color.GREEN) else cIsNew.setTextColor(Color.RED)
            if (coin.is_active) cIsActive.setTextColor(Color.GREEN) else cIsActive.setTextColor(
                Color.RED
            )
            itemCoin.setOnClickListener {
                Intent(it.context, CoinDetailsActivity::class.java).apply {
                    putExtra(ID, coin.id)
                    it.context.startActivity(this)
                }
            }
        }
    }

    override fun getItemCount(): Int = coinList.size

    private fun photoSelector(id: String, bind: ItemCoinBinding) {
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