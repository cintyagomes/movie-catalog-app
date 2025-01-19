package com.omdbapi.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.omdbapi.MainActivity
import com.omdbapi.R
import com.omdbapi.data.model.MovieDetail
import com.omdbapi.databinding.FragmentMovieDetailBinding
import com.omdbapi.di.DaggerAppComponent
import com.omdbapi.presentation.viewModel.MovieViewModel
import com.omdbapi.presentation.viewModel.MovieViewModelFactory

// Fragment for displaying detailed information about a movie
class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {

    private lateinit var binding: FragmentMovieDetailBinding // Binding for fragment layout
    private lateinit var viewModel: MovieViewModel // ViewModel for managing UI-related data

    // Inflates the fragment's layout and initializes the ViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false) // Binds the layout

        // Creates the AppComponent for Dagger dependency injection and gets the repository
        val appComponent = DaggerAppComponent.create()
        val repository = appComponent.movieRepository()

        // Creates and sets the ViewModel using the repository
        viewModel = ViewModelProvider(
            this,
            MovieViewModelFactory(repository)
        )[MovieViewModel::class.java]

        return binding.root // Returns the root view of the fragment
    }

    // Initializes the UI elements and observes ViewModel state changes
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieves the movie ID from the arguments and fetches movie details
        val movieId = arguments?.let { MovieDetailFragmentArgs.fromBundle(it).movieId }

        // Observes the movie details state and updates the UI accordingly
        viewModel.movieDetails.observe(viewLifecycleOwner) { state ->
            when(state) {
                is MovieViewModel.UIState.Loading -> showLoading()
                is MovieViewModel.UIState.Loaded -> showMovieDetails(state.data)
                is MovieViewModel.UIState.Error -> showError(state.message)
            }
        }

        // Fetches movie details if a valid apiKey is provided
        movieId?.let { viewModel.getMovieDetails("431d51d7", it) }
    }

    // Displays the loading state with a progress bar
    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.itemMovieDetail.root.visibility = View.GONE
    }

    // Displays the movie details in the UI
    private fun showMovieDetails(movieDetail: MovieDetail) {
        binding.progressBar.visibility = View.GONE
        binding.itemMovieDetail.root.visibility = View.VISIBLE

        // Sets up the toolbar for navigation
        binding.itemMovieDetail.toolbar.apply {
            (activity as? AppCompatActivity)?.setSupportActionBar(this)
            (activity as? AppCompatActivity)?.supportActionBar?.setDisplayShowTitleEnabled(false)
            setNavigationIcon(R.drawable.ic_arrow_back)

            // Handles the back navigation when the icon is clicked
            setNavigationOnClickListener {
                val navController = (activity as? MainActivity)?.findNavController(R.id.nav_host_fragment)
                navController?.navigateUp()
            }
        }

        binding.itemMovieDetail.title.text = movieDetail.title
        binding.itemMovieDetail.releaseDate.text = movieDetail.released
        binding.itemMovieDetail.runtime.text = movieDetail.runtime
        binding.itemMovieDetail.genre.text = movieDetail.genre
        binding.itemMovieDetail.director.text = movieDetail.director
        binding.itemMovieDetail.writer.text = movieDetail.writer
        binding.itemMovieDetail.actors.text = movieDetail.actors
        binding.itemMovieDetail.plot.text = movieDetail.plot
        binding.itemMovieDetail.language.text = movieDetail.language
        binding.itemMovieDetail.awards.text = movieDetail.awards
        binding.itemMovieDetail.imdbRating.text = movieDetail.imdbRating
        binding.itemMovieDetail.type.text = movieDetail.type

        Glide.with(binding.root)
            .load(movieDetail.poster)
            .into(binding.itemMovieDetail.poster)
    }

    // Displays an error message and hides the movie details UI
    private fun showError(message: String) {
        binding.progressBar.visibility = View.GONE
        binding.itemMovieDetail.root.visibility = View.GONE
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }
}
