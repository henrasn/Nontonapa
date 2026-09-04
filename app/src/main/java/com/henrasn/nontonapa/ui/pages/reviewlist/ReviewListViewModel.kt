package com.henrasn.nontonapa.ui.pages.reviewlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.henrasn.nontonapa.data.model.uimodel.movie.MovieUiData
import com.henrasn.nontonapa.data.model.uimodel.moviereview.MovieReviewUiData
import com.henrasn.nontonapa.data.repo.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewListViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ReviewListUiState())
    val uiState = _uiState.asStateFlow()

    private val movieId = MutableStateFlow<Int?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val reviewsPaging: Flow<PagingData<MovieReviewUiData>> = movieId
        .distinctUntilChanged { old, new -> old!=new }
        .filterNotNull()
        .flatMapLatest { id ->
            reviewRepository.getMovieReviewsPaged(id)
                .onStart { _uiState.update { it.copy(isLoading = true) } }
                .onEach { _uiState.update { it.copy(isLoading = false) } }
        }
        .cachedIn(viewModelScope)

    fun setMovie(id: Int) {
        movieId.value = id
    }
}
