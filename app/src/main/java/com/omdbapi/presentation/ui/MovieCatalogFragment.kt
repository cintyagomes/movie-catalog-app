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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.omdbapi.MainActivity
import com.omdbapi.R
import com.omdbapi.databinding.FragmentMovieCatalogBinding
import com.omdbapi.di.DaggerAppComponent
import com.omdbapi.presentation.viewModel.MovieViewModel
import com.omdbapi.presentation.viewModel.MovieViewModelFactory

// Fragment for displaying a catalog of movies
class MovieCatalogFragment : Fragment() {

    private lateinit var viewModel: MovieViewModel // ViewModel for managing UI-related data
    private lateinit var binding: FragmentMovieCatalogBinding // Binding for fragment layout
    private val adapter = MovieAdapter() // Adapter for displaying movie items in RecyclerView

    // Inflates the fragment's layout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieCatalogBinding.inflate(inflater, container, false) // Binds the layout
        return binding.root // Returns the root view of the fragment
    }

    // Initializes the ViewModel, UI elements, and observers
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Creates the AppComponent for Dagger dependency injection and gets the repository
        val appComponent = DaggerAppComponent.create()
        val repository = appComponent.movieRepository()

        // Creates and sets the ViewModel using the repository
        viewModel = ViewModelProvider(
            this,
            MovieViewModelFactory(repository)
        )[MovieViewModel::class.java]

        // Sets up the toolbar
        binding.toolbar.apply {
            (activity as? AppCompatActivity)?.setSupportActionBar(this) // Sets the action bar
            (activity as? AppCompatActivity)?.supportActionBar?.setDisplayShowTitleEnabled(false) // Hides the title
            setNavigationIcon(R.drawable.ic_arrow_back) // Sets the back navigation icon

            // Handles the back navigation when the icon is clicked
            setNavigationOnClickListener {
                val navController = (activity as? MainActivity)?.findNavController(R.id.nav_host_fragment)
                navController?.navigateUp() // Navigates back
            }
        }

        // Sets up the RecyclerView with a LinearLayoutManager and the movie adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        // Observes changes to the movies list and updates the adapter
        viewModel.movies.observe(viewLifecycleOwner) { movies ->
            adapter.submitList(movies) // Updates the RecyclerView with the new list of movies
        }

        // Sets the item click listener for navigating to movie detail page
        adapter.setOnItemClickListener { movieId ->
            val action = MovieCatalogFragmentDirections
                .actionMovieCatalogFragmentToMovieDetailFragment(movieId) // Creates navigation action
            findNavController().navigate(action) // Navigates to the movie detail fragment
        }

        // Retrieves the query parameter from the arguments and fetches the movies
        val query = arguments?.let { MovieCatalogFragmentArgs.fromBundle(it).query }
        query?.let {
            viewModel.getMovies("431d51d7", it) // Fetches movies based on the query
        }

        // Observes error messages and displays them as Toast notifications
        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show() // Shows error message as a Toast
        }
    }
}
