package com.lisko.mealfactory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lisko.mealfactory.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var splashBinding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashBinding= ActivitySplashBinding.inflate(layoutInflater)
        setContentView(splashBinding.root)



    }
}