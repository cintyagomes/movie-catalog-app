package com.omdbapi.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omdbapi.domain.model.Movie
import com.omdbapi.databinding.ItemMovieBinding

// Adapter for displaying a list of movies in a RecyclerView
internal class MovieAdapter :
    ListAdapter<Movie, MovieAdapter.MovieViewHolder>(MovieDiffCallback()) {

    // Variable to hold the click listener for movie items
    private var onItemClickListener: ((String) -> Unit)? = null

    // Creates a new ViewHolder for each movie item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(
            LayoutInflater.from(
                parent.context
            ), parent, false
        ) // Inflates the item layout
        return MovieViewHolder(binding) // Returns a new ViewHolder with the binding
    }

    // Binds the movie data to the ViewHolder
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position) // Gets the movie at the current position
        holder.bind(movie, onItemClickListener) // Binds the movie data and click listener
    }

    // Sets the item click listener to handle clicks on movie items
    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }

    // ViewHolder class for binding the movie data to the view
    class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // Binds the movie data to the view and sets up the click listener
        fun bind(movie: Movie, onItemClickListener: ((String) -> Unit)?) {
            binding.title.text = movie.title
            binding.year.text = movie.year
            binding.type.text = movie.type
            Glide.with(binding.root)
                .load(movie.poster)
                .into(binding.poster)

            binding.root.setOnClickListener {
                // Invokes the item click listener with the movie's IMDb ID
                onItemClickListener?.invoke(movie.imdbId)
            }
        }
    }

    // DiffUtil callback for optimizing item changes in the RecyclerView
    class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
        // Checks if the movie items are the same based on IMDb ID
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.imdbId == newItem.imdbId
        }

        // Checks if the movie content is the same
        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}
