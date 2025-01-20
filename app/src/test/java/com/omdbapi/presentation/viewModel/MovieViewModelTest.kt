package com.omdbapi.presentation.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.omdbapi.data.MovieRepository
import com.omdbapi.data.model.Movie
import com.omdbapi.data.model.MovieDetail
import com.omdbapi.data.model.MovieResponse
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.jupiter.api.DisplayName

@ExperimentalCoroutinesApi
class MovieViewModelTest {

    // Rule to ensure that LiveData runs synchronously during tests
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val repository: MovieRepository = mockk(relaxed = true) // Mocking the MovieRepository
    private var viewModel: MovieViewModel = mockk(relaxed = true) // Mocking the MovieViewModel
    private val movieObserver: Observer<MovieViewModel.UIState<List<Movie>>> =
        mockk(relaxed = true) // Observer for movie list
    private val movieDetailsObserver: Observer<MovieViewModel.UIState<MovieDetail>> =
        mockk(relaxed = true) // Observer for movie details state

    private val testDispatcher =
        StandardTestDispatcher() // Test dispatcher for coroutines, to control the execution

    private val apiKey = "431d51d7"
    private val id = "tt1234567"

    @Before
    fun setUp() {
        // Sets the main dispatcher to use the test dispatcher during tests
        Dispatchers.setMain(testDispatcher)
        // Initializes the ViewModel with the mocked repository
        viewModel = MovieViewModel(repository)

        // Adds observers to the ViewModel's LiveData so we can check if they are updated
        viewModel.movies.observeForever(movieObserver)
        viewModel.movieDetails.observeForever(movieDetailsObserver)
    }

    @DisplayName(
        """
            GIVEN a successful movie list response
            WHEN getMovies is called
            THEN the movie list is observed
        """
    )
    @Test
    fun `test getMovies success`() = runTest {
        // GIVEN
        val movieList = listOf(
            Movie("Movie 1", "2022", "tt1234567", "movie", "poster_url"),
            Movie("Movie 2", "2023", "tt7654321", "movie", "poster_url")
        )
        val response = Result.success(MovieResponse(movieList, "2", "True"))

        // Mocks the repository's getMovies method to return the success response
        coEvery { repository.getMovies(apiKey, "query") } returns response

        // WHEN
        viewModel.fetchMovies(apiKey, "query") // Calls the method to get movies from the ViewModel

        // THEN
        advanceUntilIdle() // Advances coroutines until all tasks complete

        // Verifies that the observer was notified with the correct movie list
        coVerify { movieObserver.onChanged(MovieViewModel.UIState.Loaded(movieList)) }
    }

    @DisplayName(
        """
            GIVEN a failure response
            WHEN getMovies is called
            THEN an error message is observed
        """
    )
    @Test
    fun `test getMovies failure`() = runTest {
        // GIVEN
        val exception = Exception("Error fetching movies")
        val response = Result.failure<MovieResponse>(exception)

        // Mocks the repository's getMovies method to return the failure response
        coEvery { repository.getMovies(apiKey, "query") } returns response

        // WHEN
        viewModel.fetchMovies(
            apiKey, "query"
        ) // Calls the method to get movies from the ViewModel

        // THEN
        advanceUntilIdle() // Advances coroutines until all tasks complete

        // Verifies that the error observer was notified with the correct error message
        coVerify { movieObserver.onChanged(MovieViewModel.UIState.Error("Error fetching movies")) }
    }

    @DisplayName(
        """
            GIVEN a successful movie detail response
            WHEN getMovieDetails is called
            THEN the movie details are observed
        """
    )
    @Test
    fun `test getMovieDetails success`() = runTest {
        // GIVEN
        val movieDetail = MovieDetail(
            "Movie 1", "2022", "120 min", "Action", "Director",
            "Writer", "Actors", "Plot", "English", "Awards",
            "8.5", "tt1234567", "movie", "poster_url"
        )
        val response = Result.success(movieDetail)

        // Mocks the repository's getMovieDetails method to return the success response
        coEvery { repository.getMovieDetails(apiKey, id) } returns response

        // WHEN
        viewModel.fetchMovieDetails(
            apiKey, id
        ) // Calls the method to get movie details from the ViewModel

        // THEN
        advanceUntilIdle() // Advances coroutines until all tasks complete

        // Verifies that the observer was notified with the correct movie details
        coVerify { movieDetailsObserver.onChanged(MovieViewModel.UIState.Loaded(movieDetail)) }
    }

    @DisplayName(
        """
            GIVEN a failure response
            WHEN getMovieDetails is called
            THEN an error message is observed
        """
    )
    @Test
    fun `test getMovieDetails failure`() = runTest {
        // GIVEN
        val exception = Exception("Error fetching movie details")
        val response = Result.failure<MovieDetail>(exception)

        // Mocks the repository's getMovieDetails method to return the failure response
        coEvery { repository.getMovieDetails(apiKey, id) } returns response

        // WHEN
        viewModel.fetchMovieDetails(
            apiKey, id
        ) // Calls the method to get movie details from the ViewModel

        // THEN
        advanceUntilIdle() // Advances coroutines until all tasks complete

        // Verifies that the error observer was notified with the correct error message
        coVerify {
            movieDetailsObserver.onChanged(MovieViewModel.UIState.Error("Error fetching movie details"))
        }
    }

    @After
    fun tearDown() {
        // Removes observers from LiveData after the test
        viewModel.movies.removeObserver(movieObserver)
        viewModel.movieDetails.removeObserver(movieDetailsObserver)

        // Resets the main dispatcher to its original state
        Dispatchers.resetMain()
    }
}
