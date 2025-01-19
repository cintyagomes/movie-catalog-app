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

@ExperimentalCoroutinesApi
class MovieViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val repository: MovieRepository = mockk(relaxed = true)
    private var viewModel: MovieViewModel = mockk(relaxed = true)
    private val movieObserver: Observer<List<Movie>> = mockk(relaxed = true)
    private val errorObserver: Observer<String> = mockk(relaxed = true)
    private val movieDetailsObserver: Observer<MovieViewModel.UIState<MovieDetail>> = mockk(relaxed = true)

    private val testDispatcher = StandardTestDispatcher()

    private val apiKey = "431d51d7"
    private val id = "tt1234567"

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = MovieViewModel(repository)

        viewModel.movies.observeForever(movieObserver)
        viewModel.error.observeForever(errorObserver)
        viewModel.movieDetails.observeForever(movieDetailsObserver)
    }

    @Test
    fun `test getMovies success`() = runTest {
        val movieList = listOf(
            Movie("Movie 1", "2022", "tt1234567", "movie", "poster_url"),
            Movie("Movie 2", "2023", "tt7654321", "movie", "poster_url")
        )
        val response = Result.success(MovieResponse(movieList, "2", "True"))

        coEvery { repository.getMovies(apiKey, "query") } returns response

        viewModel.getMovies(apiKey, "query")

        advanceUntilIdle()

        coVerify { movieObserver.onChanged(movieList) }
    }

    @Test
    fun `test getMovies failure`() = runTest {
        val exception = Exception("Error fetching movies")
        val response = Result.failure<MovieResponse>(exception)

        coEvery { repository.getMovies(apiKey, "query") } returns response

        viewModel.getMovies(apiKey, "query")

        advanceUntilIdle()

        coVerify { errorObserver.onChanged("Error fetching movies") }
    }

    @Test
    fun `test getMovieDetails success`() = runTest {
        val movieDetail = MovieDetail(
            "Movie 1", "2022", "120 min", "Action", "Director", "Writer", "Actors", "Plot",
            "English", "Awards", "8.5", "tt1234567", "movie", "poster_url"
        )
        val response = Result.success(movieDetail)

        coEvery { repository.getMovieDetails(apiKey, id) } returns response

        viewModel.getMovieDetails(apiKey, id)

        advanceUntilIdle()

        coVerify { movieDetailsObserver.onChanged(MovieViewModel.UIState.Loaded(movieDetail)) }
    }

    @Test
    fun `test getMovieDetails failure`() = runTest {
        val exception = Exception("Error fetching movie details")
        val response = Result.failure<MovieDetail>(exception)

        coEvery { repository.getMovieDetails(apiKey, id) } returns response

        viewModel.getMovieDetails(apiKey, id)

        advanceUntilIdle()

        coVerify { errorObserver.onChanged("Error fetching movie details") }
    }

    @After
    fun tearDown() {
        viewModel.movies.removeObserver(movieObserver)
        viewModel.error.removeObserver(errorObserver)
        viewModel.movieDetails.removeObserver(movieDetailsObserver)

        Dispatchers.resetMain()
    }
}
