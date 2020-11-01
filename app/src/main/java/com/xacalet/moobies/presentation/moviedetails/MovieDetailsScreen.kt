package com.xacalet.moobies.presentation.moviedetails

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Box
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.ui.tooling.preview.Devices
import androidx.ui.tooling.preview.Preview
import com.xacalet.domain.model.Genre
import com.xacalet.domain.model.MovieDetails
import com.xacalet.moobies.R
import com.xacalet.moobies.presentation.moviedetails.ui.RatingSection
import com.xacalet.moobies.presentation.ui.LightBlue700
import dev.chrisbanes.accompanist.coil.CoilImage
import java.time.LocalDate

@Composable
fun MovieDetailsScreen(
    movie: MovieDetails,
    backdropImageUrl: State<String?>,
    posterImageUrl: State<String?>,
    isWishlisted: State<Boolean>,
    userRating: State<Byte?>,
    onWishlistToggled: () -> Unit,
    onUserRatingClicked: () -> Unit
) {
    ScrollableColumn {
        Surface(elevation = 2.dp) {
            Column(modifier = Modifier.padding(bottom = 32.dp)) {
                DetailHeader(
                    titleText = movie.title ?: "",
                    imageUrl = backdropImageUrl.value
                ) {
                    Row {
                        ProvideTextStyle(MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Light)) {
                            ProvideEmphasis(AmbientEmphasisLevels.current.medium) {
                                Text(movie.releaseDate?.year?.toString() ?: "")
                                val runtime = movie.runtime.let {
                                    "${it.div(60)}h ${it % 60}min"
                                }
                                Text(runtime, Modifier.padding(start = 8.dp))
                            }
                        }

                    }
                }
                Divider(modifier = Modifier.padding(top = 16.dp))
                DetailOverview(
                    posterImageUrl.value,
                    movie.genres.map { it.name },
                    movie.overview ?: ""
                )
                Divider(modifier = Modifier.padding(top = 16.dp))
                // Button wishlist
                WishlistTextButton(
                    isWishlisted = isWishlisted,
                    onWishlistToggled = onWishlistToggled,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 32.dp)
                )
                Divider(modifier = Modifier.padding(top = 16.dp, bottom = 12.dp))
                RatingSection(movie.voteAverage, movie.voteCount, userRating, onUserRatingClicked)
            }
        }
        Surface(modifier = Modifier.height(128.dp).padding(top = 64.dp)) { }
    }
}

@Composable
fun DetailHeader(
    imageUrl: String?,
    titleText: String,
    subtitleSlot: @Composable BoxScope.() -> Unit
) {
    ConstraintLayout {
        val (image, title, subtitle, button) = createRefs()
        CoilImage(
            data = imageUrl ?: "",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .height(220.dp)
                .fillMaxWidth()
                .constrainAs(image) {
                    top.linkTo(parent.top, margin = 4.dp)
                }
        )
        Text(
            text = titleText,
            style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Light),
            modifier = Modifier
                .constrainAs(title) {
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(button.start, margin = 16.dp)
                    top.linkTo(image.bottom, margin = 16.dp)
                    width = Dimension.fillToConstraints
                }
        )
        IconButton(
            onClick = { /* TODO */ },
            icon = { Icon(Icons.Default.MoreVert) },
            modifier = Modifier
                .constrainAs(button) {
                    end.linkTo(parent.end)
                    top.linkTo(title.top)
                }
        )
        Box(
            modifier = Modifier.constrainAs(subtitle) {
                start.linkTo(parent.start, margin = 16.dp)
                top.linkTo(title.bottom, margin = 4.dp)
            }
        ) {
            subtitleSlot()
        }
    }
}

@Composable
fun DetailOverview(
    imageUrl: String?,
    buttonTexts: List<String>,
    descriptionText: String
) {
    Column {
        ConstraintLayout(
            Modifier
                .padding(top = 16.dp, start = 16.dp)
                .fillMaxWidth()
        ) {
            val (image, genreList, overview, button) = createRefs()
            CoilImage(
                data = imageUrl ?: "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(120.dp)
                    .height(180.dp)
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
            )

            Column(
                modifier = Modifier
                    .constrainAs(genreList) {
                        top.linkTo(parent.top)
                        start.linkTo(image.end, 16.dp)
                        end.linkTo(parent.end, 16.dp)
                        width = Dimension.fillToConstraints
                    }
            ) {
                ScrollableRow {
                    buttonTexts.forEach { text ->
                        Button(
                            onClick = {},
                            modifier = Modifier.padding(end = 8.dp),
                            colors = ButtonConstants.defaultOutlinedButtonColors(
                                contentColor = MaterialTheme.colors.onSurface
                            ),
                            border = BorderStroke(
                                1.dp,
                                MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
                            )
                        ) {
                            Text(
                                text = text,
                                modifier = Modifier.padding(top = 2.dp, bottom = 2.dp),
                                style = MaterialTheme.typography.button.copy(fontWeight = FontWeight.Light)
                            )
                        }
                    }
                }
                Text(
                    text = descriptionText,
                    modifier = Modifier.padding(top = 12.dp),
                    maxLines = 5,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun WishlistTextButton(
    modifier: Modifier = Modifier,
    isWishlisted: State<Boolean>,
    onWishlistToggled: () -> Unit
) {
    val interactionState: InteractionState = remember { InteractionState() }
    if (isWishlisted.value) {
        interactionState.addInteraction(Interaction.Pressed)
    } else {
        interactionState.removeInteraction(Interaction.Pressed)
    }
    Button(
        onClick = onWishlistToggled,
        modifier = modifier.zIndex(0F),
        border = BorderStroke(1.dp, LightBlue700),
        interactionState = interactionState,
        elevation = ButtonConstants.defaultElevation(2.dp, 0.dp, 0.dp),
        colors = if (isWishlisted.value) {
            ButtonConstants.defaultOutlinedButtonColors(
                contentColor = MaterialTheme.colors.onSurface
            )
        } else {
            ButtonConstants.defaultButtonColors(
                backgroundColor = LightBlue700,
                contentColor = Color.White
            )
        }
    ) {
        Row(
            modifier = Modifier
                .padding(top = 4.dp, bottom = 4.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(if (isWishlisted.value) Icons.Default.Check else Icons.Default.Add)
            Text(
                stringResource(if (isWishlisted.value) R.string.added_to_wishlist else R.string.add_to_wishlist),
                Modifier.padding(start = 16.dp),
                style = MaterialTheme.typography.button.copy(fontWeight = FontWeight.Normal)
            )
        }
    }
}

@Composable
@Preview(
    name = "Detail screen in Default",
    showBackground = true,
    showDecoration = true,
    device = Devices.PIXEL_3A
)
@Preview(
    name = "Detail screen in Spanish",
    showBackground = true,
    showDecoration = true,
    locale = "es",
    device = Devices.PIXEL_3A
)
fun PreviewDetailsScreen() {
    val movie = MovieDetails(
        id = 0,
        backdropPath = "",
        posterPath = "",
        genres = listOf(Genre(1, "Drama"), Genre(2, "Action")),
        originalTitle = "A movie title that is a bit long",
        overview = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
        releaseDate = LocalDate.now(),
        runtime = 143,
        title = "A movie title that is a bit long",
        voteAverage = 8.3,
        voteCount = 1234,
    )
    val isWishlisted = remember { mutableStateOf(true) }
    val backdropImageUrl: State<String?> = remember { mutableStateOf("") }
    val posterImageUrl: State<String?> = remember { mutableStateOf("") }
    val userRating: State<Byte?> = remember { mutableStateOf(6) }
    MovieDetailsScreen(
        movie = movie,
        backdropImageUrl = backdropImageUrl,
        posterImageUrl = posterImageUrl,
        isWishlisted = isWishlisted,
        userRating = userRating,
        onWishlistToggled = { isWishlisted.value = !isWishlisted.value },
        onUserRatingClicked = {}
    )
}

