package com.example.task1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.io.Reader
import java.nio.file.Files
import java.nio.file.Paths

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var reader: Reader = Files.newBufferedReader(Paths.get("res/raw/list.json"))


    }
}