package com.xacalet.moobies.presentation.home

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.*
import com.google.accompanist.pager.*
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
        val scrollState = rememberScrollState()
        Column(Modifier.verticalScroll(scrollState)) {
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
    var parentBoxWidth by remember { mutableStateOf(0) }
    Box(
        Modifier
            .height(240.dp)
            .onGloballyPositioned { layoutCoordinates ->
                parentBoxWidth = layoutCoordinates.size.width
            }) {
        when (val state = popularShowsDataState.collectAsState().value) {
            is PopularShowsDataState.Error -> {
                // TODO: Implement Error screen.
            }
            PopularShowsDataState.Loading -> {
                PagerLoading()
            }
            is PopularShowsDataState.Ready -> {
                //val startIndex = 0
                val startIndex = Int.MAX_VALUE / 4
                val pagerState = rememberPagerState(initialPage = startIndex)
                HorizontalPager(
                    count = Int.MAX_VALUE / 2,
                    //count = state.data.count(),
                    state = pagerState
                ) { index ->
                    val page = (index - startIndex).floorMod(state.data.count())
                    //val page = index
                    val offset =
                        (-parentBoxWidth / 8).dp * (pagerState.currentPageOffset + (pagerState.currentPage - index))
                    Log.d(
                        "HEADERX",
                        "width = $parentBoxWidth -- ${pagerState.currentPageOffset} + ${pagerState.currentPage} - $index = ${calculateCurrentOffsetForPage(index)}"
                    )
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
        modifier = Modifier.align(Alignment.Center).fillMaxSize(),
        iterations = LottieConstants.IterateForever,
        dynamicProperties = dynamicProperties
    )
}

private fun Int.floorMod(other: Int): Int = when (other) {
    0 -> this
    else -> this - floorDiv(other) * other
}
