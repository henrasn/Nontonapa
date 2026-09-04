package com.henrasn.nontonapa.ui.pages.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.henrasn.nontonapa.core.error.toUiText
import com.henrasn.nontonapa.data.model.uimodel.moviereview.MovieReviewUiData
import com.henrasn.nontonapa.data.repo.MovieDetailRepository
import com.henrasn.nontonapa.data.repo.ReviewRepository
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
    private val movieDetailRepository: MovieDetailRepository,
    private val reviewRepository: ReviewRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(MovieDetailUiState())
    val uiState: StateFlow<MovieDetailUiState> = _uiState.asStateFlow()

    private val _effect = Channel<MovieDetailEffect>()
    val effect = _effect.receiveAsFlow()

    fun onIntent(intent: MovieDetailIntent) {
        when (intent) {
            is MovieDetailIntent.LoadMovieDetail -> loadMovieDetail(intent.movieId)
            is MovieDetailIntent.LoadMovieReviews -> loadMovieReviews(intent.movieId)
        }
    }

    private fun loadMovieDetail(movieId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            movieDetailRepository.getMovieDetail(movieId)
                .onSuccess { movie ->
                    _uiState.update { it.copy(movie = movie, isLoading = false) }
                }
                .onFailure { error ->
                    _uiState.update { it.copy(isLoading = false) }
                    _effect.send(MovieDetailEffect.ShowError(error.toUiText()))
                }
        }
    }

    private fun loadMovieReviews(movieId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isReviewLoading = true) }

            reviewRepository.getMovieReviews(movieId)
                .onSuccess { reviews ->
                    _uiState.update { it.copy(reviews = reviews, isReviewLoading = false) }
                }
                .onFailure { error ->
                    _uiState.update { it.copy(isReviewLoading = false) }
                    _effect.send(MovieDetailEffect.ShowError(error.toUiText()))
                }
        }
    }
}
