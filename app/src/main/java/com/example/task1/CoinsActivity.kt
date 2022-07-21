package com.example.task1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.task1.FileUtils.Companion.getList
import com.example.task1.databinding.ActivityCoinsBinding

const val TAG = "MainActivity"
const val ID = "ID"

class CoinsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoinsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val list = getList(this, R.raw.list2)


        if (list.isNotEmpty()) {
            binding.txt1.text = list[0].toString()
            binding.txt2.text = list[1].toString()
            binding.txt3.text = list[2].toString()
        }

        binding.txt1.setOnClickListener {
            Intent(this, CoinDetailsActivity::class.java).also {
                it.putExtra(ID, list[0].id)
                startActivity(it)
            }
        }
        binding.txt2.setOnClickListener {
            Intent(this, CoinDetailsActivity::class.java).also {
                it.putExtra(ID, list[1].id)
                startActivity(it)
            }
        }
        binding.txt3.setOnClickListener {
            Intent(this, CoinDetailsActivity::class.java).also {
                it.putExtra(ID, list[2].id)
                startActivity(it)
            }
        }
    }
}