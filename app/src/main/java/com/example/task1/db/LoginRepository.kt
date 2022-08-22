package com.example.task1.db

import android.content.Context
import com.example.task1.retrofit.LoginRepository

class LoginRepositoryDB(context: Context) {
    private var repo: LoginRepository = LoginRepository()
    private var dao: MoviesDao? = MovieDBSingelton.getInstance(context).getMovieDB()

}