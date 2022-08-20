package com.example.task1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.task1.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.show()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view_tag) as NavHostFragment
        val navController = navHostFragment.navController

        if (savedInstanceState == null) {
            navController.navigate(R.id.loginFragment)
        }
    }
}