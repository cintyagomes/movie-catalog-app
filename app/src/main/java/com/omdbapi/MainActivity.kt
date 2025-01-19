package com.omdbapi

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

// MainActivity for initializing the main screen with the navigation graph
class MainActivity : AppCompatActivity() {
    // Initializes the activity and sets the content view to the navigation graph
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Sets the content view to the navigation graph, using the activity_main navigation resource
        setContentView(R.navigation.activity_main)
    }
}
