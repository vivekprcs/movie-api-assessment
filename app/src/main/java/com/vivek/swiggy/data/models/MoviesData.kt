package com.vivek.swiggy.data.models

data class MoviesData(val Title: String, val Year : String, val Released: String,
                      val Genre: String, val Director: String, val Actors: String,
                      val Plot: String, val Poster: String,
    val imdbRating: String, val Language: String, val imdbID: String)

data class SearchData(val Search: List<MoviesData>?)
