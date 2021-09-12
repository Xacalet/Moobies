package com.xacalet.moobies.presentation.main

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.xacalet.moobies.R
import com.xacalet.moobies.presentation.account.AccountDirections
import com.xacalet.moobies.presentation.home.HomeDirections
import com.xacalet.moobies.presentation.search.SearchDirections
import com.xacalet.moobies.presentation.video.VideoDirections
import com.xacalet.moobies.presentation.ui.MoobiesTheme
import com.xacalet.moobies.presentation.ui.SelectedContentColor
import com.xacalet.moobies.presentation.ui.UnselectedContentColor

sealed class Tab(@StringRes val title: Int, val icon: ImageVector, val route: String) {
    object Home : Tab(R.string.home_tab, Icons.Filled.Home, HomeDirections.Root.route)
    object Search : Tab(R.string.search_tab, Icons.Filled.Search, SearchDirections.Root.route)
    object Video : Tab(R.string.video_tab, Icons.Filled.PlayCircle, VideoDirections.Root.route)
    object Account : Tab(R.string.account_tab, Icons.Filled.Person, AccountDirections.Root.route)
}

@Composable
fun MainBottomBar(navController: NavController) {
    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        listOf(Tab.Home, Tab.Search, Tab.Video, Tab.Account).forEach { tab ->
            BottomNavigationItem(
                icon = { Icon(tab.icon, stringResource(tab.title), Modifier.size(24.dp)) },
                label = { Text(stringResource(tab.title)) },
                selected = currentDestination?.hierarchy?.any { it.route == tab.route } == true,
                onClick = {
                    navController.navigate(tab.route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                selectedContentColor = SelectedContentColor,
                unselectedContentColor = UnselectedContentColor
            )
        }
    }
}

@Preview
@Composable
internal fun MainBottomBarPreview() {
    val navController = rememberNavController()
    MoobiesTheme {
        MainBottomBar(navController)
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
internal fun MainBottomBarDarkPreview() {
    val navController = rememberNavController()
    MoobiesTheme {
        MainBottomBar(navController)
    }
}
