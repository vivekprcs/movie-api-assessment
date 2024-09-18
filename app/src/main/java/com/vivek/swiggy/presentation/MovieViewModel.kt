package com.vivek.swiggy.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vivek.swiggy.data.models.ApiResponse
import com.vivek.swiggy.data.models.MoviesData
import com.vivek.swiggy.data.models.SearchData
import com.vivek.swiggy.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val movieRepository: MovieRepository) :ViewModel() {

    private val _searchUiState = MutableStateFlow<ApiResponse<SearchData>>(ApiResponse.Idle)
    val searchUiState: StateFlow<ApiResponse<SearchData>> = _searchUiState.asStateFlow()

    private val _detailState = MutableStateFlow<ApiResponse<MoviesData>>(ApiResponse.Idle)
    val detailState: StateFlow<ApiResponse<MoviesData>> = _detailState.asStateFlow()


    fun getMoviesList(movieName:String){
     viewModelScope.launch {
         movieRepository.getMovies(movieName).collect {
          _searchUiState.value = it
         }
     }
    }

    fun getMoviesDetail(imdbId:String){
        viewModelScope.launch {
            movieRepository.getMoviesDetail(imdbId).collect {
                _detailState.value = it
            }
        }
    }
}