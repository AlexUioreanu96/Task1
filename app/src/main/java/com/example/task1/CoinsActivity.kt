package com.example.task1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.task1.FileUtils.Companion.getList
import com.example.task1.databinding.ActivityCoinsBinding

const val TAG = "MainActivity"


class CoinsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoinsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val list = getList(this, R.raw.list2)


        if (list.isNotEmpty()) {
            binding.txt1.text = list[0].toString()
            binding.txt2.text = list[1].toString()
            binding.txt3.text = list[2].toString()
        }
    }
}