package com.henrasn.nontonapa.ui.pages.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.henrasn.nontonapa.core.error.toUiText
import com.henrasn.nontonapa.data.repo.MovieDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    val movieDetailRepository: MovieDetailRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(MovieDetailUiState())
    val uiState: StateFlow<MovieDetailUiState> = _uiState.asStateFlow()

    private val _effect = Channel<MovieDetailEffect>()
    val effect = _effect.receiveAsFlow()

    fun onIntent(intent: MovieDetailIntent) {
        when (intent) {
            is MovieDetailIntent.LoadMovieDetail -> loadData(intent.movieId)
        }
    }

    private fun loadData(movieId: Int) {
        _uiState.update { it.copy(isLoading = true, isReviewLoading = true) }
        viewModelScope.launch { loadMovieDetail(movieId) }
        viewModelScope.launch { loadMovieReviews(movieId) }
    }

    private suspend fun loadMovieDetail(movieId: Int) {
        movieDetailRepository.getMovieDetail(movieId)
            .onSuccess { movie ->
                _uiState.update { it.copy(movie = movie, isLoading = false) }
            }
            .onFailure { error ->
                _uiState.update { it.copy(isLoading = false) }
                _effect.send(MovieDetailEffect.ShowError(error.toUiText()))
            }
    }

    private suspend fun loadMovieReviews(movieId: Int) {
        movieDetailRepository.getMovieReviews(movieId)
            .onSuccess { reviews ->
                _uiState.update { it.copy(reviews = reviews, isReviewLoading = false) }
            }
            .onFailure { error ->
                _uiState.update { it.copy(isReviewLoading = false) }
                _effect.send(MovieDetailEffect.ShowError(error.toUiText()))
            }
    }
}
