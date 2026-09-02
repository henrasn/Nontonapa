package com.henrasn.nontonapa.ui.pages.genre

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.henrasn.nontonapa.data.repo.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieGenreViewModel @Inject constructor(
    val movieRepository: MovieRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(MovieGenreUiState())
    val uiState: StateFlow<MovieGenreUiState> = _uiState.asStateFlow()

    fun onIntent(intent: MovieGenreIntent) {
        when (intent) {
            MovieGenreIntent.loadMovieGenres -> loadMovieGenres(false)
            MovieGenreIntent.refreshPage -> loadMovieGenres(true)
        }
    }

    private fun loadMovieGenres(isRefresh: Boolean) {
        viewModelScope.launch {
            _uiState.update {
                if (it.isRefreshing) it.copy(isRefreshing = true)
                else it.copy(isLoading = true)
            }

            movieRepository.getMovieGenres()
                .onSuccess { genres ->
                    _uiState.update {
                        it.copy(genres = genres, isLoading = false, isRefreshing = false)
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            error = error.message ?: error.localizedMessage ?: "",
                            isLoading = false,
                            isRefreshing = false
                        )
                    }
                }
        }
    }
}