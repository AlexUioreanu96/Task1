package com.example.task1

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class FileUtilsModel {

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
    }

}