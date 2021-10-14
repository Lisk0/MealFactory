package com.lisko.mealfactory.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.lisko.mealfactory.databinding.FragmentMealDetailsBinding

class MealDetailsFragment : Fragment(){
    private lateinit var binding: FragmentMealDetailsBinding
    val args: MealDetailsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMealDetailsBinding.inflate(layoutInflater)
        binding.mealId.text= args.mealId.toString()
        return binding.root
    }



}