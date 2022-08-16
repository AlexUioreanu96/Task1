package com.example.task1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task1.adapter.CoinAdapter
import com.example.task1.databinding.ActivityCoinsBinding
import com.example.task1.util.FileUtils.Companion.getList

const val TAG = "MainActivity"


class CoinsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoinsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val list = getList(this, R.raw.list2)

        val adapterObj = CoinAdapter(list)

        binding.coinRecycler.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = adapterObj
        }

    }
}