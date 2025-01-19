package com.omdbapi.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.omdbapi.MainActivity
import com.omdbapi.R
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

        binding.toolbar.apply {
            (activity as? AppCompatActivity)?.setSupportActionBar(this)
            (activity as? AppCompatActivity)?.supportActionBar?.setDisplayShowTitleEnabled(false)
            setNavigationIcon(R.drawable.ic_arrow_back)

            setNavigationOnClickListener {
                val navController = (activity as? MainActivity)?.findNavController(R.id.nav_host_fragment)
                navController?.navigateUp()
            }
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        viewModel.movies.observe(viewLifecycleOwner) { movies ->
            adapter.submitList(movies)
        }

        adapter.setOnItemClickListener { movieId ->
            val action = MovieCatalogFragmentDirections
                .actionMovieCatalogFragmentToMovieDetailFragment(movieId)
            findNavController().navigate(action)
        }

        val query = arguments?.let { MovieCatalogFragmentArgs.fromBundle(it).query }
        query?.let {
            viewModel.getMovies("431d51d7", it)
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }
    }
}
