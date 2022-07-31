package com.example.task1

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.task1.adapter.GridAdapter
import com.example.task1.databinding.ActivityCoinDetailsBinding
import com.example.task1.models.CoinDetailsDTO

class CoinDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoinDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCoinDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setSupportActionBar(binding.toolbar)
//        supportActionBar?.hide()

        var extras: String? = intent.getStringExtra(ID)

        extras = extras?.replace('-', '_')

        val coin = extras?.let { FileUtils.getObj(this, it) }

        populateView(coin)

    }

    private fun populateView(coin: CoinDetailsDTO?) {
        coin?.let {
            binding.apply {
                txtRank.text = "${coin.rank}."
                txtName.text = coin.name
                txtSymbol.text = "(${coin.symbol})"
                if (coin.isActive) txtIsActive.apply {
                    text = "active"
                    setTextColor(Color.GREEN)
                } else txtIsActive.apply {
                    text = "unactive"
                    setTextColor(Color.RED)
                }
                txtDescription.text = coin.description
                txtTmName1.text = coin.team[0].name
                txtTmPosition1.text = coin.team[0].position
                txtTmName2.text = coin.team[1].name
                txtTmPosition2.text = coin.team[1].position

                val listNameTag = coin.tags.map { it.name }

                gridView.adapter = GridAdapter(applicationContext, listNameTag)
            }
        }
    }
}