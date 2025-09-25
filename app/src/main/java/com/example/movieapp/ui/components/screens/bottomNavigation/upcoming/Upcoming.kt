package com.example.movieapp.ui.components.screens.bottomNavigation.upcoming

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
import com.example.movieapp.ui.components.MovieListItem

@Composable
fun Upcoming(
    genres: ArrayList<Genre>? = null,
    navController: NavController
) {
    val upComingViewModel = hiltViewModel<UpComingViewModel>()
    MovieListItem(
        movies = upComingViewModel.upcomingMovies,
        genres = genres,
        selectedName = upComingViewModel.selectedGenre.value,
        navController = navController
    ) {
        upComingViewModel.filterData.value = GenreId(it?.id.toString())
        it?.let {
            upComingViewModel.selectedGenre.value = it
        }
    }
}