package com.vivek.swiggy.data.repository

import com.vivek.swiggy.data.ApiService
import com.vivek.swiggy.data.models.ApiResponse
import com.vivek.swiggy.data.models.MoviesData
import com.vivek.swiggy.data.models.SearchData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class MovieRepository(val apiService: ApiService) {

    suspend fun getMovies(searchQuery:String) : Flow<ApiResponse<SearchData>> = flow{
        emit(ApiResponse.Loading)
        try {
         val searchData = apiService.getMoviesList(searchQuery)
            emit(ApiResponse.Success(searchData))
        }
        catch (e:IOException){
            emit(ApiResponse.Error("Network Error"))

        }
        catch(e:HttpException){
          emit(ApiResponse.Error(""+e.code()+ "Http Error"))
        }
        catch (e:Exception){
            emit(ApiResponse.Error("Something went wrong"))
        }
    }

    suspend fun getMoviesDetail(imdbId:String) :Flow<ApiResponse<MoviesData>> = flow{
        emit(ApiResponse.Loading)
        try {
            val movieDetail = apiService.getMoviesDetail(imdbId)
            emit(ApiResponse.Success(movieDetail))
        }
        catch (e:IOException){
            emit(ApiResponse.Error("Network Error"))

        }
        catch(e:HttpException){
            emit(ApiResponse.Error(""+e.code()+ "Http Error"))
        }
        catch (e:Exception){
            emit(ApiResponse.Error("Something went wrong"))
        }

    }

}