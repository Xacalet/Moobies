package com.xacalet.moobies.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.*
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerScope
import com.google.accompanist.pager.rememberPagerState
import com.xacalet.moobies.R
import com.xacalet.moobies.presentation.components.CardTitle
import com.xacalet.moobies.presentation.components.SectionCard
import com.xacalet.moobies.presentation.components.SectionTitle
import com.xacalet.moobies.presentation.ui.Yellow600
import kotlinx.coroutines.flow.StateFlow

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel,
    openShowDetail: (Long) -> Unit
) {
    Surface {
        Column {
            PopularMoviesSection(
                popularShowsDataState = viewModel.popularShowsDataState,
                toggleWishlist = viewModel::toggleWishlist,
                openShowDetail = openShowDetail
            )
            Spacer(Modifier.size(24.dp))
            SectionTitle(
                modifier = Modifier.padding(16.dp),
                text = "What to see"
            )
            UpcomingMoviesSection(viewModel, openShowDetail)
        }
    }
}

@Composable
private fun UpcomingMoviesSection(
    viewModel: HomeScreenViewModel,
    openShowDetail: (Long) -> Unit
) {
    SectionCard(header = { CardTitle(stringResource(R.string.upcoming_movies)) }) {
        val upcomingMovies = viewModel.upcomingMoviesFlow.collectAsLazyPagingItems()
        LazyRow(contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)) {
            items(upcomingMovies) { entry ->
                if (entry != null) {
                    ShowItem(
                        data = entry,
                        onWishlistButtonClicked = viewModel::toggleWishlist,
                        onClick = openShowDetail
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalPagerApi::class)
private fun PopularMoviesSection(
    popularShowsDataState: StateFlow<PopularShowsDataState>,
    toggleWishlist: (Long) -> Unit,
    openShowDetail: (Long) -> Unit
) {
    // TODO: Add automatic transition.
    Box(Modifier.height(240.dp)) {
        when (val state = popularShowsDataState.collectAsState().value) {
            is PopularShowsDataState.Error -> {
                // TODO: Implement Error screen.
            }
            PopularShowsDataState.Loading -> {
                PagerLoading()
            }
            is PopularShowsDataState.Ready -> {
                val pagerState = rememberPagerState(
                    pageCount = state.data.count(),
                    initialOffscreenLimit = 1,
                    infiniteLoop = true
                )
                // TODO: Look for options for smoothing transitions.
                HorizontalPager(pagerState) { page ->
                    val offset =
                        (-128).dp * calculateCurrentOffsetForPage(page, pagerState.pageCount)
                    HeaderItem(
                        modifier = Modifier.height(240.dp),
                        itemModifier = Modifier.offset(x = offset),
                        data = state.data[page],
                        onShowClick = openShowDetail,
                        onWishlistButtonClick = toggleWishlist
                    )
                }
            }
        }
    }
}

@Composable
fun BoxScope.PagerLoading() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_animation))
    val dynamicProperties = rememberLottieDynamicProperties(
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = Yellow600.toArgb(),
            keyPath = arrayOf("**")
        ),
    )
    LottieAnimation(
        composition = composition,
        modifier = Modifier.align(Alignment.Center),
        iterations = LottieConstants.IterateForever,
        dynamicProperties = dynamicProperties
    )
}

/**
 * Workaround to built-in's PagerScope.calculateCurrentOffsetForPage not working when transitioning
 * between first and last page when infinite loop mode is activated.
 */
@OptIn(ExperimentalPagerApi::class)
fun PagerScope.calculateCurrentOffsetForPage(page: Int, pageCount: Int): Float {
    return if ((page > currentPage + 1) && (currentPageOffset > 0))
        (currentPage + currentPageOffset) - page + pageCount
    else if ((currentPage > page + 1) && (currentPageOffset > 0))
        (currentPage + currentPageOffset) - page - pageCount
    else if ((currentPage < page) && (currentPageOffset < 0))
        (currentPage + currentPageOffset) - page + pageCount
    else
        (currentPage + currentPageOffset) - page
}
