package com.example.task1

import android.app.Application
import com.example.task1.db.MovieRepository

class MovieApplication : Application() {
    val repository: MovieRepository by lazy {
        MovieRepository(this)
    }

    override fun onCreate() {
        super.onCreate()
        repository
    }

    override fun onTerminate() {
        super.onTerminate()
    }
}