package com.omdbapi.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.omdbapi.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

// Fragment for handling the search functionality and navigating to movie catalog
@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding // Binding for fragment layout

    // Inflates the fragment's layout and initializes the binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false) // Binds the layout
        return binding.root // Returns the root view of the fragment
    }

    // Initializes the UI elements and sets up listeners
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Sets up the click listener for the search button
        binding.searchBtn.setOnClickListener {
            val query = binding.searchEditText.text.toString() // Retrieves the query from the EditText

            // Checks if the query is not blank and navigates to the movie catalog screen
            if (query.isNotBlank()) {
                val action = SearchFragmentDirections.actionSearchToResults(query) // Creates navigation action
                findNavController().navigate(action)
            } else {
                // Shows a Toast if the query is blank
                Toast.makeText(requireContext(), "Enter a search term", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
