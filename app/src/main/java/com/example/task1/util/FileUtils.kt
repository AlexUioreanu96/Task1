package com.example.task1.util

import android.content.Context
import android.util.Log
import com.example.task1.TAG
import com.example.task1.models.CoinDetailsDTO
import com.example.task1.models.CoinModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class FileUtils {

    companion object {
        fun getList(context: Context, id: Int): List<CoinModel> {
            lateinit var jsonString: String
            try {
                jsonString = context.resources.openRawResource(id)
                    .bufferedReader()
                    .use { it.readText() }
            } catch (ioException: IOException) {
                Log.w(TAG, "bad reading")
                return emptyList()
            }

            val listCountryType = object : TypeToken<List<CoinModel>>() {}.type
            return Gson().fromJson(jsonString, listCountryType)
        }

        fun getObj(context: Context, id: String): CoinDetailsDTO {
            lateinit var jsonString: String
            try {
                jsonString = context.assets.open("$id.json")
                    .bufferedReader()
                    .use { it.readText() }
            } catch (ioException: IOException) {
                Log.w(TAG, "bad reading")
                return CoinDetailsDTO()
            }

            return Gson().fromJson(jsonString, CoinDetailsDTO::class.java)
        }
    }
}