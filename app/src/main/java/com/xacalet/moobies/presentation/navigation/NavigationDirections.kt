package com.xacalet.moobies.presentation.navigation

import androidx.navigation.NamedNavArgument
import java.util.Collections.emptyList

abstract class NavigationCommand(
    val route: String,
    open val arguments: List<NamedNavArgument> = emptyList()
)
