package com.vivek.swiggy.data

import com.vivek.swiggy.data.models.MoviesData
import com.vivek.swiggy.data.models.SearchData
import retrofit2.http.GET
import retrofit2.http.Query

//https://www.omdbapi.com/?apikey=9a167949&s=smurfs&page=1
//Ex: https://www.omdbapi.com/?i=tt0472181&apikey=9a167949

interface ApiService {
    @GET("?apikey=9a167949")
    suspend fun getMoviesList(@Query("s") movie: String,@Query("page") page: Int = 1): SearchData

    @GET("?apikey=9a167949")
    suspend fun getMoviesDetail(@Query("i") imdbId: String): MoviesData
}