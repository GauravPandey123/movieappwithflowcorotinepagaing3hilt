package com.example.movieapp.ui.components.screens.mainscreen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.createGraph
import androidx.navigation.navArgument
import com.example.movieapp.R
import com.example.movieapp.model.Genre
import com.example.movieapp.model.Genres
import com.example.movieapp.ui.components.CircularIndeterminateProgressBar
import com.example.movieapp.ui.components.appbar.AppBarWithArrow
import com.example.movieapp.ui.components.appbar.HomeAppBar
import com.example.movieapp.ui.components.appbar.SearchBar
import com.example.movieapp.ui.components.screens.bottomNavigation.nowplaying.NowPlaying
import com.example.movieapp.ui.components.screens.bottomNavigation.popular.Popular
import com.example.movieapp.ui.components.screens.bottomNavigation.toprated.TopRated
import com.example.movieapp.ui.components.screens.bottomNavigation.upcoming.Upcoming
import com.example.movieapp.ui.components.screens.component.SearchUI
import com.example.movieapp.ui.components.screens.movieDetail.MovieDetail
import com.example.movieapp.ui.navigation.Navigation
import com.example.movieapp.ui.navigation.Screen
import com.example.movieapp.ui.navigation.currentRoute
import com.example.movieapp.ui.navigation.navigationTitle
import com.example.movieapp.ui.theme.FloatingActionBackground
import com.example.movieapp.utils.AppConstant
import com.example.movieapp.utils.network.DataState
import com.example.movieapp.utils.networkconnection.ConnectionState
import com.example.movieapp.utils.networkconnection.connectivityState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("MutableCollectionMutableState")
@Composable
fun MainScreen() {
    val mainViewModel = hiltViewModel<MainViewModel>()
    val navController = rememberNavController()
    val scaffoldState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val isAppBarVisible = remember { mutableStateOf(true) }
    val searchProgressBar = remember { mutableStateOf(false) }
    val genreName = remember { mutableStateOf("") }
    val genreList = remember { mutableStateOf(arrayListOf<Genre>()) }
    // internet connection
    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available

    // genre api call for first time
    LaunchedEffect(key1 = 0) {
        mainViewModel.genreList()
    }

    if(mainViewModel.genres.value is DataState.Success<Genres> ) {
        genreList.value =
            (mainViewModel.genres.value as DataState.Success<Genres>)
                .data.genres as ArrayList
        // All first value as all
        if (genreList.value.first().name != AppConstant.DEFAULT_GENRE_ITEM)
            genreList.value.add(0, Genre(null, AppConstant.DEFAULT_GENRE_ITEM))
    }

    Scaffold(topBar = {
        when (currentRoute(navController)) {
            Screen.Home.route, Screen.Popular.route, Screen.TopRated.route, Screen.Upcoming.route, Screen.NavigationDrawer.route -> {
                if (isAppBarVisible.value) {
                    val appTitle: String =
                        if (currentRoute(navController) == Screen.NavigationDrawer.route) genreName.value
                        else stringResource(R.string.app_title)
                    HomeAppBar(title = appTitle, openDrawer = {
                        scope.launch {

                        }
                    }, openFilters = {
                        isAppBarVisible.value = false
                    })
                } else {
                    SearchBar(isAppBarVisible, mainViewModel)
                }
            }
            else -> {
                AppBarWithArrow(navigationTitle(navController)) {
                    navController.popBackStack()
                }
            }

        }
    }, floatingActionButton = {
        when (currentRoute(navController)) {
            Screen.Home.route, Screen.Popular.route, Screen.TopRated.route, Screen.Upcoming.route -> {
                FloatingActionButton(
                    onClick = {
                        isAppBarVisible.value = false
                    },
                    containerColor = FloatingActionBackground
                ) {
                    Icon(Icons.Filled.Search, "", tint = Color.White)
                }
            }
        }
    }, bottomBar = {
        when (currentRoute(navController)) {
            Screen.Home.route, Screen.Popular.route, Screen.TopRated.route, Screen.Upcoming.route -> {
                  BottomNavigationUI(
                    navController
                )
            }
        }
    }, snackbarHost = {
        if (isConnected.not()) {
            Snackbar(
                action = {}, modifier = Modifier.padding(8.dp)
            ) {
                Text(text = stringResource(R.string.there_is_no_internet))
            }
        }
    }) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Navigation(navController, Modifier.padding(it), genreList.value)
            Column {
                CircularIndeterminateProgressBar(isDisplayed = searchProgressBar.value, 0.1f)
                if (isAppBarVisible.value.not()) {
                    SearchUI(navController, mainViewModel.searchData) {
                        isAppBarVisible.value = true
                    }
                }
            }
        }

    }
}

@Composable
fun BottomNavigationUI(navController: NavController) {
    val items = listOf(
        Screen.HomeNav,
        Screen.PopularNav,
        Screen.TopRatedNav,
        Screen.UpcomingNav,
    )
    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                label = { Text(text = stringResource(id = item.title)) },
                selected = currentRoute(navController) == item.route,
                icon = item.navIcon,
                onClick = {
                    navController.navigate(item.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                })
        }
    }

}


