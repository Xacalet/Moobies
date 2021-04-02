package com.xacalet.moobies.presentation.userrating

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.request.ImageRequest
import coil.transform.BlurTransformation
import com.xacalet.moobies.R
import com.xacalet.moobies.presentation.components.ShowSimpleList
import com.xacalet.moobies.presentation.components.StarRatingInput
import com.xacalet.moobies.presentation.ui.Gray800
import com.xacalet.moobies.presentation.ui.Gray900
import com.xacalet.moobies.presentation.ui.MoobiesTheme
import com.xacalet.moobies.presentation.ui.verticalGradientBackground
import dev.chrisbanes.accompanist.coil.CoilImage
import kotlinx.coroutines.launch
import java.util.*

// TODO: Add transitions when they get available for compose-navigation
@ExperimentalMaterialApi
@Composable
fun UserRatingScreen(
    showId: Long,
    navController: NavHostController,
    viewModel: UserRatingViewModel,
) {
    viewModel.onRatingChanged.observeAsState().value?.let {
        val text = stringResource(R.string.rating_saved)
        Toast.makeText(LocalContext.current, text, Toast.LENGTH_SHORT).show()
        close(navController)
    }

    viewModel.onRatingRemoved.observeAsState().value?.let {
        val text = stringResource(R.string.rating_removed)
        Toast.makeText(LocalContext.current, text, Toast.LENGTH_SHORT).show()
        close(navController)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        contentColor = Color.White
    ) {
        viewModel.data.observeAsState().value?.let {
            UserRatingScreenContent(
                it,
                { close(navController) },
                onRatingChanged = { stars -> viewModel.onRatingChanged(stars) },
                onRatingRemoved = { viewModel.onRatingRemoved() },
                viewModel.otherRatedShows.observeAsState(GetOtherRatedShowsState.Loading),
                onBottomSheetExpanded = { rating ->
                    viewModel.fetchOtherRatedShows(showId, rating, 100)
                }
            )
        }
        viewModel.isLoading.observeAsState().value?.let { isLoading ->
            if (isLoading) {
                CircularProgressIndicator(Modifier.wrapContentSize(Alignment.Center))
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun UserRatingScreenContent(
    data: UserRatingUiModel,
    onBack: () -> Unit,
    onRatingChanged: (Int) -> Unit,
    onRatingRemoved: () -> Unit,
    otherShowsRated: State<GetOtherRatedShowsState>,
    onBottomSheetExpanded: (Int) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val (stars, onStarsChanged) = remember { mutableStateOf(data.stars) }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            onBottomSheetExpanded(stars ?: 0)
            BottomSheetContent(
                sheetState = sheetState,
                stars = stars,
                otherTitlesWithSameRating = otherShowsRated
            )
        },
        sheetBackgroundColor = Gray900,
        sheetContentColor = LocalContentColor.current
    ) {
        Box(contentAlignment = Alignment.TopStart) {
            CoilImage(
                request = ImageRequest.Builder(LocalContext.current)
                    .data(data.poserImageUrl ?: "")
                    .transformations(BlurTransformation(LocalContext.current, 8f, 20f))
                    .build(),
                contentDescription = null,
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop
            )
            Spacer(
                modifier = Modifier
                    .matchParentSize()
                    .verticalGradientBackground(listOf(Color(0x40000000), Color(0xE0000000)))
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                UserRatingTopBar(
                    onBack = onBack,
                    modifier = Modifier.padding(16.dp)
                )

                Box(
                    modifier = Modifier
                        .weight(1F)
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    if (stars != null) {
                        Text(
                            text = stars.toString(),
                            style = MaterialTheme.typography.h1
                        )
                    } else {
                        CoilImage(
                            data = data.poserImageUrl ?: "",
                            contentDescription = null,
                            modifier = Modifier
                                .width(180.dp)
                                .height(270.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .weight(1F)
                        .fillMaxHeight()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = stringResource(R.string.rating_question, data.showName),
                        style = MaterialTheme.typography.h5,
                        textAlign = TextAlign.Center
                    )

                    Spacer(Modifier.size(16.dp))

                    StarRatingInput(rating = stars, onRatingChanged = onStarsChanged)

                    Spacer(Modifier.size(16.dp))

                    ProvideTextStyle(value = MaterialTheme.typography.subtitle1) {
                        Button(
                            onClick = { stars?.let(onRatingChanged) },
                            enabled = stars != null,
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.DarkGray,
                                contentColor = LocalContentColor.current
                            ),
                            elevation = ButtonDefaults.elevation(2.dp, 0.dp, 0.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = stringResource(R.string.rate).toUpperCase(Locale.getDefault()),
                                style = MaterialTheme.typography.subtitle1
                            )
                        }

                        if (data.stars != null) {
                            Spacer(Modifier.size(16.dp))
                            Button(
                                onClick = { onRatingRemoved() },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color.Transparent,
                                    contentColor = LocalContentColor.current
                                ),
                                elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp),
                                modifier = Modifier.wrapContentWidth()
                            ) {
                                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                                    Text(
                                        text = stringResource(R.string.remove_rating).toUpperCase(
                                            Locale.getDefault()
                                        ),
                                        style = MaterialTheme.typography.subtitle1
                                    )
                                }
                            }
                        }
                    }
                }
            }
            if (stars != null) {
                val coroutineScope = rememberCoroutineScope()
                OtherRatedTitlesHeader(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    stars = stars
                ) {
                    IconButton(onClick = { coroutineScope.launch { sheetState.show() } }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowUp,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun UserRatingTopBar(
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onBack
        ) {
            Icon(
                imageVector = Icons.Outlined.Close,
                contentDescription = null
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            var switchState by remember { mutableStateOf(false) }
            Text(stringResource(R.string.share_rating))
            Spacer(modifier = Modifier.size(16.dp))
            Switch(
                checked = switchState,
                onCheckedChange = { switchState = !switchState },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colors.primary,
                    uncheckedThumbColor = Color.LightGray,
                    uncheckedTrackColor = Color.LightGray
                )
            )
        }
    }
}


@Composable
fun OtherRatedTitlesHeader(
    modifier: Modifier = Modifier,
    stars: Int,
    actionButton: @Composable BoxScope.() -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Gray800)
            .padding(start = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(stringResource(R.string.other_titles_you_rated_x_y, stars, 10))
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            actionButton()
        }
    }
}

@ExperimentalMaterialApi
@Composable
internal fun BottomSheetContent(
    sheetState: ModalBottomSheetState,
    stars: Int?,
    otherTitlesWithSameRating: State<GetOtherRatedShowsState>
) {
    val coroutineScope = rememberCoroutineScope()
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        OtherRatedTitlesHeader(
            stars = stars ?: 0,
            modifier = Modifier.shadow(2.dp)
        ) {
            IconButton(onClick = { coroutineScope.launch { sheetState.hide() } }) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null
                )
            }
        }
        when (val value = otherTitlesWithSameRating.value) {
            is GetOtherRatedShowsState.Loading -> {
                Box(Modifier.height(64.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(Modifier.wrapContentSize(Alignment.Center))
                }
            }
            is GetOtherRatedShowsState.Result -> {
                if (value.shows.isEmpty()) {
                    Box(
                        modifier = Modifier.height(64.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(stringResource(R.string.no_other_titles_with_this_rating))
                    }
                } else {
                    ShowSimpleList(value.shows) {}
                }
            }
        }
    }
}

private fun close(navController: NavController) {
    navController.popBackStack()
}

@ExperimentalMaterialApi
@Composable
@Preview(showBackground = true)
fun PreviewUserRatingScreen() {
    MoobiesTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            contentColor = Color.White
        ) {
            UserRatingScreenContent(
                data = UserRatingUiModel(1, "Maze Runner: The Scorch Trials", 7, ""),
                onBack = {},
                onRatingChanged = {},
                onRatingRemoved = {},
                otherShowsRated = mutableStateOf(GetOtherRatedShowsState.Loading),
                onBottomSheetExpanded = {}
            )
        }
    }
}
