package com.example.movieapp.ui.components.screens.bottomNavigation.popular

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.movieapp.model.Genre
import com.example.movieapp.model.GenreId
import com.example.movieapp.ui.components.MovieItemView
import com.example.movieapp.ui.components.MovieListItem

@Composable
fun Popular(
    genres: ArrayList<Genre>? = null,
    navController: NavController

) {
    val popularViewModel = hiltViewModel<PopularViewModel>()
    MovieListItem(
        movies = popularViewModel.popularMovies,
        genres = genres,
        selectedName = popularViewModel.selectedGenre.value,
        navController = navController
    ){
        popularViewModel.filterData.value = GenreId(it?.id.toString())
        it?.let {
            popularViewModel.selectedGenre.value = it
        }
    }
}