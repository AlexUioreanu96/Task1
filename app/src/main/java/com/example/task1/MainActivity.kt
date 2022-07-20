package com.example.task1

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }


    fun getList(context: Context): List<crypto> {

        lateinit var jsonString: String
        try {
            jsonString = context.assets.open("assets/list2.json")
                .bufferedReader()
                .use { it.readText() }
        } catch (ioException: IOException) {
            println("///...///")
        }

        val listCountryType = object : TypeToken<List<crypto>>() {}.type
        return Gson().fromJson(jsonString, listCountryType)
    }

}