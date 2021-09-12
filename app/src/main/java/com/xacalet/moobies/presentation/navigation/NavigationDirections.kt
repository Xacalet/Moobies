package com.xacalet.moobies.presentation.navigation

import androidx.navigation.compose.NamedNavArgument

abstract class NavigationCommand(
    val route: String,
    open val arguments: List<NamedNavArgument> = emptyList()
)
