package com.xacalet.moobies.presentation.userrating

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import androidx.ui.tooling.preview.Preview
import coil.request.ImageRequest
import coil.transform.BlurTransformation
import com.xacalet.moobies.R
import com.xacalet.moobies.presentation.components.ShowSimpleList
import com.xacalet.moobies.presentation.ui.*
import dev.chrisbanes.accompanist.coil.CoilImage
import java.util.*

@ExperimentalMaterialApi
@Composable
fun UserRatingScreen(
    showId: Long,
    onClose: () -> Unit
) {
    val viewModel = viewModel<UserRatingViewModel>()

    viewModel.setId(showId)

    val data by viewModel.data.observeAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        contentColor = Color.White
    ) {
        data?.let {
            UserRatingScreenContent(
                it,
                onClose,
                onRatingChanged = { stars ->
                    viewModel.onRatingChanged(stars)
                    onClose()
                },
                onRatingRemoved = {
                    viewModel.onRatingRemoved()
                    onClose()
                },
                viewModel.otherRatedShows.observeAsState(GetOtherRatedShowsState.Loading),
                onBottomSheetExpanded = { rating ->
                    viewModel.fetchOtherRatedShows(showId, rating, 100)
                }
            )
        } ?: CircularProgressIndicator(Modifier.wrapContentSize(Alignment.Center))
    }
}

@ExperimentalMaterialApi
@Composable
fun UserRatingScreenContent(
    data: UserRatingUiModel,
    onBack: () -> Unit,
    onRatingChanged: (Byte) -> Unit,
    onRatingRemoved: () -> Unit,
    otherShowsRated: State<GetOtherRatedShowsState>,
    onBottomSheetExpanded: (Byte) -> Unit
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
        sheetContentColor = AmbientContentColor.current
    ) {
        Box(alignment = Alignment.TopStart) {
            CoilImage(
                modifier = Modifier.matchParentSize(),
                request = ImageRequest.Builder(ContextAmbient.current)
                    .data(data.poserImageUrl ?: "")
                    .transformations(BlurTransformation(ContextAmbient.current, 8f, 20f))
                    .build(),
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
                    alignment = Alignment.Center
                ) {
                    if (stars != null) {
                        Text(
                            text = stars.toString(),
                            style = MaterialTheme.typography.h1
                        )
                    } else {
                        CoilImage(
                            modifier = Modifier.width(180.dp).height(270.dp),
                            data = data.poserImageUrl ?: "",
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

                    Spacer(Modifier.preferredSize(16.dp))

                    StarRatingInput(rating = stars, onRatingChanged = onStarsChanged)

                    Spacer(Modifier.preferredSize(16.dp))

                    ProvideTextStyle(value = MaterialTheme.typography.subtitle1) {
                        Button(
                            onClick = { stars?.let(onRatingChanged) },
                            enabled = stars != null,
                            colors = ButtonConstants.defaultButtonColors(
                                backgroundColor = Color.DarkGray,
                                contentColor = AmbientContentColor.current
                            ),
                            elevation = ButtonConstants.defaultElevation(2.dp, 0.dp, 0.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = stringResource(R.string.rate).toUpperCase(Locale.getDefault()),
                                style = MaterialTheme.typography.subtitle1
                            )
                        }

                        if (data.stars != null) {
                            Spacer(Modifier.preferredSize(16.dp))
                            Button(
                                onClick = { onRatingRemoved() },
                                colors = ButtonConstants.defaultButtonColors(
                                    backgroundColor = Color.Transparent,
                                    contentColor = AmbientContentColor.current
                                ),
                                elevation = ButtonConstants.defaultElevation(0.dp, 0.dp, 0.dp),
                                modifier = Modifier.wrapContentWidth()
                            ) {
                                Providers(AmbientContentAlpha provides ContentAlpha.medium) {
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
                OtherRatedTitlesHeader(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    stars = stars
                ) {
                    IconButton(onClick = { sheetState.show() }) {
                        Icon(Icons.Default.KeyboardArrowUp)
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
            Icon(Icons.Outlined.Close)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            var switchState by remember { mutableStateOf(false) }
            Text(stringResource(R.string.share_rating))
            Spacer(modifier = Modifier.preferredSize(16.dp))
            Switch(
                checked = switchState,
                onCheckedChange = { switchState = !switchState },
                colors = SwitchConstants.defaultColors(
                    checkedThumbColor = MaterialTheme.colors.primary,
                    uncheckedThumbColor = Color.LightGray,
                    uncheckedTrackColor = Color.LightGray
                )
            )
        }
    }
}

@Composable
fun StarRatingInput(
    rating: Byte?,
    onRatingChanged: (Byte?) -> Unit,
    starCount: Int = 10,
) {
    Row(
        modifier = Modifier.height(40.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        (1..starCount).forEach { index ->
            Box(
                modifier = Modifier
                    .weight(1F)
                    .clickable(onClick = { onRatingChanged(index.toByte()) }),
                alignment = Alignment.Center
            ) {
                Image(
                    asset = Icons.Default.Star,
                    colorFilter = ColorFilter.tint(
                        if (index <= rating ?: 0) Blue600 else Color.Gray
                    ),
                    modifier = Modifier.size(
                        if (index <= rating ?: 0) 32.dp else 24.dp
                    )
                )
            }
        }
    }
}

@Composable
fun OtherRatedTitlesHeader(
    modifier: Modifier = Modifier,
    stars: Byte,
    actionButton: @Composable BoxScope.() -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .preferredHeight(50.dp)
            .background(Gray800)
            .padding(start = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(stringResource(R.string.other_titles_you_rated_x_y, stars, 10))
        Box(
            modifier = Modifier.fillMaxWidth(),
            alignment = Alignment.CenterEnd
        ) {
            actionButton()
        }
    }
}

@ExperimentalMaterialApi
@Composable
internal fun BottomSheetContent(
    sheetState: ModalBottomSheetState,
    stars: Byte?,
    otherTitlesWithSameRating: State<GetOtherRatedShowsState>
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        OtherRatedTitlesHeader(
            stars = stars ?: 0,
            modifier = Modifier.drawShadow(2.dp)
        ) {
            IconButton(onClick = { sheetState.hide() }) {
                Icon(Icons.Default.KeyboardArrowDown)
            }
        }
        when (val value = otherTitlesWithSameRating.value) {
            is GetOtherRatedShowsState.Loading -> {
                Box(Modifier.preferredHeight(64.dp), alignment = Alignment.Center) {
                    CircularProgressIndicator(Modifier.wrapContentSize(Alignment.Center))
                }
            }
            is GetOtherRatedShowsState.Result -> {
                if (value.shows.isEmpty()) {
                    Box(
                        modifier = Modifier.preferredHeight(64.dp),
                        alignment = Alignment.Center
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

@Composable
@Preview(showBackground = true)
fun PreviewStarRatingInput() {
    val (rating, onRatingChanged) = remember { mutableStateOf<Byte?>(null) }
    StarRatingInput(rating, onRatingChanged)
}
