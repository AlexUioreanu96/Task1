package com.example.task1

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.task1.databinding.ActivityCoinsBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

const val TAG = "MainActivity"


class CoinsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoinsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val list = getList(this)

        if (list.isNotEmpty()) {
            binding.txt1.text = list[0].toString()
            binding.txt2.text = list[1].toString()
            binding.txt3.text = list[2].toString()
        }
    }


    private fun getList(context: Context): List<CoinModel> {
        lateinit var jsonString: String
        try {
            jsonString = context.resources.openRawResource(R.raw.list2)
                .bufferedReader()
                .use { it.readText() }
        } catch (ioException: IOException) {
            Log.w(TAG, "bad reading")
            return emptyList()
        }

        val listCountryType = object : TypeToken<List<CoinModel>>() {}.type
        return Gson().fromJson(jsonString, listCountryType)
    }
}