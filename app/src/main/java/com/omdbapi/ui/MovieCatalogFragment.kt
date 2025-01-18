package com.omdbapi.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.omdbapi.databinding.FragmentMovieCatalogBinding
import com.omdbapi.di.DaggerAppComponent
import com.omdbapi.presentation.MovieViewModel
import com.omdbapi.presentation.MovieViewModelFactory

class MovieCatalogFragment : Fragment() {

    private lateinit var viewModel: MovieViewModel
    private lateinit var binding: FragmentMovieCatalogBinding
    private val adapter = MovieAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieCatalogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appComponent = DaggerAppComponent.create()
        val repository = appComponent.movieRepository()
        viewModel = ViewModelProvider(
            this,
            MovieViewModelFactory(repository)
        )[MovieViewModel::class.java]

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        viewModel.movies.observe(viewLifecycleOwner) { movies ->
            adapter.submitList(movies)
        }

        val query = arguments?.let { MovieCatalogFragmentArgs.fromBundle(it).query }
        query?.let {
            viewModel.getMovies("431d51d7", it)
        }
    }
}
