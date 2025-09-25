package com.example.movieapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.movieapp.R
import com.example.movieapp.model.Genre
import com.example.movieapp.ui.components.screens.bottomNavigation.nowplaying.NowPlaying
import com.example.movieapp.ui.components.screens.bottomNavigation.popular.Popular
import com.example.movieapp.ui.components.screens.bottomNavigation.toprated.TopRated
import com.example.movieapp.ui.components.screens.bottomNavigation.upcoming.Upcoming
import com.example.movieapp.ui.components.screens.movieDetail.MovieDetail

@Composable
fun Navigation(
    navController: NavHostController, modifier: Modifier, genres: ArrayList<Genre>? = null){
    NavHost(navController, startDestination = Screen.Home.route, modifier) {
        composable(Screen.Home.route) {
            NowPlaying(
                navController = navController,
                genres
            )
        }
        composable(Screen.Popular.route) {
            Popular(
                genres,
                navController = navController,

            )
        }
        composable(Screen.TopRated.route) {
            TopRated(
                genres,
                navController = navController,

            )
        }
        composable(Screen.Upcoming.route) {
            Upcoming(
                genres,
                navController = navController
            )
        }
        composable(
            Screen.NavigationDrawer.route.plus(Screen.NavigationDrawer.objectPath),
            arguments = listOf(navArgument(Screen.NavigationDrawer.objectName) {
                type = NavType.StringType
            })
        ) { backStack ->
            val genreId = backStack.arguments?.getString(Screen.NavigationDrawer.objectName)
            genreId?.let {

            }
        }
        composable(
            Screen.MovieDetail.route.plus(Screen.MovieDetail.objectPath),
            arguments = listOf(navArgument(Screen.MovieDetail.objectName) {
                type = NavType.IntType
            })
        ) {
           // label = stringResource("MovieDetail")
            val movieId = it.arguments?.getInt(Screen.MovieDetail.objectName)
            movieId?.let {
                MovieDetail(
                    navController = navController, movieId
                )
            }
        }
    }

}
@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route?.substringBeforeLast("/")
}

@Composable
fun navigationTitle(navController: NavController): String {
    return when (currentRoute(navController)) {
        Screen.MovieDetail.route -> stringResource(id = R.string.movie_detail)
        Screen.ArtistDetail.route -> stringResource(id = R.string.artist_detail)
        Screen.Login.route -> stringResource(id = R.string.login)
        else -> {
            ""
        }
    }
}
