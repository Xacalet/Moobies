package com.xacalet.moobies.presentation.navigation

import androidx.navigation.NavType
import androidx.navigation.compose.navArgument

object CommonDirections {

    object ShowDetails : NavigationCommand("showDetails/{showId}") {

        const val SHOW_ID = "showId"

        override val arguments = listOf(navArgument(SHOW_ID) { type = NavType.LongType })

        fun createDestination(showId: Long): String = "showDetails/$showId"
    }

    object UserRating : NavigationCommand("common/userRating/{showId}") {

        const val SHOW_ID = "showId"

        override val arguments = listOf(navArgument(SHOW_ID) { type = NavType.LongType })

        fun createDestination(showId: Long): String = "common/userRating/$showId"
    }
}
