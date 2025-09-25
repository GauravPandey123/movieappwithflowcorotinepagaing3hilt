package com.example.movieapp.ui.components.screens.mainscreen

import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.movieapp.model.movieDetail.Genre

class Navigation(navController: NavHostController, modifier: Modifier, genres: ArrayList<Genre>? = null,) {


//    Scaffold(
//        modifier = Modifier
//            .fillMaxSize(),
//        bottomBar = { BottomNavigationBar(navController) }
//    ) { innerPadding ->
//
//        val graph =
//            navController.createGraph(startDestination = Screen.Home.route) {
//                composable(route = Screen.Home.route) {
//                    NowPlaying(navController =navController )
//                }
//                composable(route = Screen.Profile.route) {
//                    Popular(navController =navController)
//                }
//                composable(route = Screen.Cart.route) {
//                    TopRated(navController =navController)
//                }
//                composable(route = Screen.Setting.route) {
//                    Upcoming(navController =navController)
//                }
//                composable(
//                    Screen.MovieDetail.route.plus(Screen.MovieDetail.objectPath),
//                    arguments = listOf(navArgument(Screen.MovieDetail.objectName) {
//                        type = NavType.IntType
//                    })
//                ) {
//                    label = stringResource(R.string.movie_detail)
//                    val movieId = it.arguments?.getInt(Screen.MovieDetail.objectName)
//                    movieId?.let {
//                        MovieDetail(
//                            navController = navController, movieId
//                        )
//                    }
//                }
//            }
//        NavHost(
//            navController = navController,
//            graph = graph,
//            modifier = Modifier.padding(innerPadding),
//
//
//        )
//
//    }
}