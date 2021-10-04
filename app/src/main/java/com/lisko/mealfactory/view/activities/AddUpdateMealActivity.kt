package com.lisko.mealfactory.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.ui.setupActionBarWithNavController
import com.lisko.mealfactory.R
import com.lisko.mealfactory.databinding.ActivityAddUpdateMealBinding

class AddUpdateMealActivity : AppCompatActivity() {
    private lateinit var mealBinding: ActivityAddUpdateMealBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mealBinding= ActivityAddUpdateMealBinding.inflate(layoutInflater)
        setContentView(mealBinding.root)

        setupActionBar()

        mealBinding.ivAddPhoto.setOnClickListener {
            Toast.makeText(this, "Select image clicked", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupActionBar() {
        setSupportActionBar(mealBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mealBinding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

}