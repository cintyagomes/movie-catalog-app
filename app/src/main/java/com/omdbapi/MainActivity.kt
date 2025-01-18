package com.omdbapi

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.omdbapi.databinding.ActivityMainBinding
import com.omdbapi.di.DaggerAppComponent
import com.omdbapi.presentation.MovieViewModel
import com.omdbapi.presentation.MovieViewModelFactory
import com.omdbapi.ui.MovieAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MovieViewModel
    private lateinit var binding: ActivityMainBinding
    private var adapter = MovieAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val appComponent = DaggerAppComponent.create()
        val repository = appComponent.movieRepository()

        val factory = MovieViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        viewModel.movies.observe(this) { movies ->
            if (movies.isNotEmpty()) {
                adapter.submitList(movies)
            }
        }

        viewModel.error.observe(this) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.getMovies("431d51d7", "action")
    }
}
