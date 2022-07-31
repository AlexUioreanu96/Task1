package com.example.task1

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.task1.adapter.CoinTeamAdapter
import com.example.task1.adapter.GridAdapter
import com.example.task1.adapter.ID
import com.example.task1.databinding.ActivityCoinDetailsBinding
import com.example.task1.models.CoinDetailsDTO


class CoinDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoinDetailsBinding
    private lateinit var teamAdapter: CoinTeamAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCoinDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()


        var extras: String? = intent.getStringExtra(ID)

        extras = extras?.replace('-', '_')

        val coin = extras?.let { FileUtils.getObj(this, it) }

        if (coin?.team?.isNotEmpty() == true) {
            teamAdapter = CoinTeamAdapter(coin.team)
            binding.teamList.apply {
                layoutManager =
                    GridLayoutManager(applicationContext, 2, horizontalFadingEdgeLength, false)
                adapter = teamAdapter
            }
        }

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


                val listNameTag = coin.tags.map { it.name }

                gridView.adapter = GridAdapter(applicationContext, listNameTag)
            }
        }
    }
}