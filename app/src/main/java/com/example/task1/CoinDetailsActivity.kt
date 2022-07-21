package com.example.task1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.task1.databinding.ActivityCoinDetailsBinding

class CoinDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoinDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCoinDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var extras: String? = intent.getStringExtra(ID)

        extras = extras?.replace('-', '_')

        val coin = extras?.let { FileUtils.getObj(this, it) }

        coin?.let {
            binding.apply {
                txtRank.text = coin.rank.toString() + '.'
                txtName.text = coin.name
                txtSymbol
            }
        }


    }
}