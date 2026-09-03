package com.henrasn.nontonapa.ui.pages.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.henrasn.nontonapa.data.model.uimodel.movie.MovieUiData
import com.henrasn.nontonapa.data.repo.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieUiState())
    val uiState: StateFlow<MovieUiState> = _uiState.asStateFlow()

    private val genreId = MutableStateFlow<Int?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val moviesPaging: Flow<PagingData<MovieUiData>> = genreId
        .distinctUntilChanged { old, new -> old!=new }
        .filterNotNull()
        .flatMapLatest { id ->
            movieRepository.getDiscoverMovies(id)
                .onStart { _uiState.update { it.copy(isLoading = true) } }
                .onEach { _uiState.update { it.copy(isLoading = false, isRefreshing = false) } }
        }
        .cachedIn(viewModelScope)

    fun setGenre(id: Int) {
        genreId.value = id
    }
}
