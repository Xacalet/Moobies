package com.xacalet.moobies.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieAnimationSpec
import com.airbnb.lottie.compose.rememberLottieAnimationState
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import com.xacalet.moobies.R
import com.xacalet.moobies.presentation.components.CardTitle
import com.xacalet.moobies.presentation.components.SectionCard
import com.xacalet.moobies.presentation.components.SectionTitle
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow


@ExperimentalCoroutinesApi
@ExperimentalPagerApi
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

@ExperimentalCoroutinesApi
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

@ExperimentalCoroutinesApi
@ExperimentalPagerApi
@Composable
private fun PopularMoviesSection(
    popularShowsDataState: StateFlow<PopularShowsDataState>,
    toggleWishlist: (Long) -> Unit,
    openShowDetail: (Long) -> Unit
) {
    Box(Modifier.height(240.dp)) {
        when (val state = popularShowsDataState.collectAsState().value) {
            is PopularShowsDataState.Error -> {
                // TODO: Implement Error screen.
            }
            PopularShowsDataState.Loading -> {
                // TODO: Tint lottie animation in yellow.
                val animationSpec = remember { LottieAnimationSpec.RawRes(R.raw.loading_animation) }
                val animationState = rememberLottieAnimationState(autoPlay = true, repeatCount = Integer.MAX_VALUE)
                LottieAnimation(
                    animationSpec,
                    modifier = Modifier.align(Alignment.Center),
                    animationState,
                )
            }
            is PopularShowsDataState.Ready -> {
                val pagerState = rememberPagerState(
                    pageCount = state.data.count(),
                    initialOffscreenLimit = 2
                )
                HorizontalPager(pagerState) { page ->
                    val offset = (-128).dp * calculateCurrentOffsetForPage(page)
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
