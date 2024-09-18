package com.vivek.swiggy.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.vivek.swiggy.data.models.ApiResponse
import com.vivek.swiggy.data.models.MoviesData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter

@AndroidEntryPoint
class ComposeActivity : ComponentActivity() {

    val movieViewModel: MovieViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            MovieScreen(viewModel = movieViewModel) {

            }
        }
    }
}
 @OptIn(ExperimentalGlideComposeApi::class)
 @Composable
fun MovieScreen(viewModel: MovieViewModel,onItemClick : (MoviesData)->Unit) {
     var searchQuery by remember {
         mutableStateOf(TextFieldValue(""))
     }
     val searchResults by viewModel.searchUiState.collectAsState()
     val debounceQuery  = remember {
         MutableStateFlow("")
     }
     LaunchedEffect(searchQuery.text) {
         debounceQuery.value = searchQuery.text
     }

     LaunchedEffect(debounceQuery) {
         debounceQuery
             .debounce(300L)
             .filter { it.isNotEmpty() }
             .collectLatest { query ->
                 viewModel.getMoviesList(query)
             }
     }

         Column(modifier = Modifier.fillMaxSize()) {
         TextField(
             value = searchQuery,
             onValueChange = { query ->
                 searchQuery = query
                 viewModel.getMoviesList(query.text)
             },
             label = { Text("Search Movie") },
             modifier = Modifier
                 .fillMaxWidth()
                 .padding(8.dp)
         )

          when (val result = searchResults){
              is ApiResponse.Error -> {
                  Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                      Text("Error: ${result.error}")
                  }
              }

              ApiResponse.Loading -> {
                  Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                      CircularProgressIndicator()
                  }
              }
              is ApiResponse.Success -> {
                  LazyColumn(modifier = Modifier.fillMaxSize(),
                      contentPadding = PaddingValues(8.dp)
                  ) {
                      result.data.Search?.let{
                          items(result.data.Search.size){ index ->
                              val movie = result.data.Search[index]
                              Row ( modifier = Modifier
                                  .fillMaxWidth()
                                  .padding(8.dp)
                                  .clickable { onItemClick(movie) },
                                  verticalAlignment = Alignment.CenterVertically){

                                  GlideImage(model = movie.Poster, contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier
                                      .padding(end = 8.dp)
                                      .size(64.dp))

                                  Text(
                                      text = movie.Title,
                                      modifier = Modifier.padding(4.dp)
                                  )

                              }

                          }
                      }

                  }

              }

              ApiResponse.Idle -> {
              }
          }

     }
 }