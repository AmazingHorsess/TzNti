package com.amazinghorsess.client_app.navigation

import android.content.Context
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.amazinghorsess.client.config.ConfigSection
import com.amazinghorsess.client_app.presentation.home.Home
import com.amazinghorsess.navigation.Destinations
import com.amazinghorsess.scanlist.presentation.ScanListSection

@Composable
fun NavGraph(
    startDestination: String = Destinations.ClientHome,
){
    val context = LocalContext.current
    val navController = rememberNavController()
    val actions = remember(navController){Actions(navController,context)}

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ){
        homeGraph(actions)
        scanListGraph()
        configGraph()

    }

}

private fun NavGraphBuilder.homeGraph(actions: Actions){
    composable(
        route = Destinations.ClientHome,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(ANIMATION_DURATION_MILLIS),
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(ANIMATION_DURATION_MILLIS),
            )
        },
    ) {
       Home(onScanListClick = {actions.openScanList})

    }
}
private fun NavGraphBuilder.configGraph(){
    composable(
        route = Destinations.ClientConfig,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(ANIMATION_DURATION_MILLIS),
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(ANIMATION_DURATION_MILLIS),
            )
        },
    ) {
        ConfigSection()

    }


}
private fun NavGraphBuilder.scanListGraph(){
    composable(
        route = Destinations.ClientScanList,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(ANIMATION_DURATION_MILLIS),
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(ANIMATION_DURATION_MILLIS),
            )
        },
    ) {
        ScanListSection()

    }
}

internal data class Actions (
    val navController: NavHostController,
    val context: Context
){
    val openScanList: () -> Unit = {
        navController.navigate(Destinations.ClientScanList)
    }
    val openMain: () -> Unit = {
        navController.navigate(Destinations.ClientHome)
    }

    val openConfig: () -> Unit = {
        navController.navigate(Destinations.ClientConfig)
    }

    val navigateUp: () -> Unit = {
        navController.navigateUp()
    }

}





private const val ANIMATION_DURATION_MILLIS = 700