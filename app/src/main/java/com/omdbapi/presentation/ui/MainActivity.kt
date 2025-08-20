package com.omdbapi.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.omdbapi.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

// MainActivity for initializing the main screen with the navigation graph
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    // Nullable binding instance to hold the view binding object
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding

    // Called when the activity is created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using ViewBinding and assign it to the binding instance
        _binding = ActivityMainBinding.inflate(layoutInflater)
        // Set the content view to the root of the binding layout
        setContentView(binding?.root)
    }

    // Called when the activity is being destroyed
    override fun onDestroy() {
        super.onDestroy()

        // Nullify the binding instance to avoid memory leaks
        _binding = null
    }
}