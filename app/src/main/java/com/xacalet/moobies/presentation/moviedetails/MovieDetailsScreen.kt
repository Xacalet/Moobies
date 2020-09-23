package com.xacalet.moobies.presentation.moviedetails

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.annotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.ui.tooling.preview.Preview
import com.xacalet.domain.model.MovieDetails
import com.xacalet.moobies.R
import com.xacalet.moobies.presentation.ui.LightBlue700
import com.xacalet.moobies.presentation.ui.Yellow600
import dev.chrisbanes.accompanist.coil.CoilImage
import java.text.NumberFormat

@Composable
fun MovieDetailsScreen(
    movie: MovieDetails,
    backdropImageUrl: State<String?>,
    posterImageUrl: State<String?>,
    isWishlisted: State<Boolean>,
    onWishlistToggled: () -> Unit
) {
    ScrollableColumn {
        Surface(
            shape = MaterialTheme.shapes.large,
            elevation = 2.dp
        ) {
            Column(modifier = Modifier.padding(bottom = 32.dp)) {
                DetailHeader(
                    titleText = movie.title ?: "",
                    imageUrl = backdropImageUrl.value
                ) {
                    Row {
                        ProvideTextStyle(MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Light)) {
                            ProvideEmphasis(EmphasisAmbient.current.medium) {
                                Text(movie.releaseDate?.year?.toString() ?: "")
                                Text(
                                    text = formatRuntime(movie.runtime),
                                    modifier = Modifier.padding(start = 8.dp)
                                )
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
                Review(movie.voteAverage, movie.voteCount)
            }
        }
        Surface(modifier = Modifier.height(128.dp).padding(top = 64.dp)) { }
    }
}

@Composable
fun DetailHeader(
    imageUrl: String?,
    titleText: String,
    subtitleSlot: @Composable () -> Unit
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
            },
            children = subtitleSlot
        )
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
            modifier = Modifier
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
                            elevation = 0.dp,
                            backgroundColor = Color.Transparent,
                            border = BorderStroke(
                                1.dp,
                                MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
                            ),
                            contentColor = MaterialTheme.colors.onSurface
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
    Button(
        onClick = onWishlistToggled,
        modifier = modifier,
        elevation = 0.dp,
        border = BorderStroke(1.dp, LightBlue700),
        backgroundColor = if (isWishlisted.value) Color.Transparent else LightBlue700,
        contentColor = if (isWishlisted.value) MaterialTheme.colors.onSurface else Color.White
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 4.dp, bottom = 4.dp)
                .fillMaxWidth()
        ) {
            Icon(if (isWishlisted.value) Icons.Default.Check else Icons.Default.Add)
            Text(
                text = stringResource(
                    if (isWishlisted.value)
                        R.string.added_to_wishlist
                    else
                        R.string.add_to_wishlist
                ),
                modifier = Modifier.padding(start = 16.dp),
                style = MaterialTheme.typography.button.copy(fontWeight = FontWeight.Normal)
            )
        }
    }
}

@Composable
fun Review(
    voteAverage: Double,
    voteCount: Int
) {
    Row {
        //Popular rating
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1F)
        ) {
            Image(
                asset = Filled.Star,
                colorFilter = ColorFilter.tint(Yellow600),
                modifier = Modifier
                    .height(32.dp)
                    .width(32.dp)
            )
            Text(
                annotatedString {
                    withStyle(SpanStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)) {
                        append(voteAverage.toString())
                    }
                    withStyle(SpanStyle(fontSize = 16.sp)) {
                        append("/10")
                    }
                }
            )
            ProvideEmphasis(EmphasisAmbient.current.medium) {
                Text(
                    NumberFormat.getInstance().format(voteCount),
                    style = MaterialTheme.typography.body1
                )
            }
        }
        //User rating
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1F)
        ) {
            // TODO: Display layout when movie is rated.
            Image(
                asset = Filled.Star,
                colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface),
                modifier = Modifier
                    .height(32.dp)
                    .width(32.dp)
            )
            Text(
                text = stringResource(R.string.rate_this).toUpperCase(),
                style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
            )
        }
        //Review
        Box(
            modifier = Modifier.weight(1F)
        )
    }
}


private fun formatRuntime(runtime: Int): String = "${runtime.div(60)}h ${runtime % 60}min"

@Composable
@Preview
fun PreviewHeader() {
    DetailHeader(
        titleText = "A movie title that is a bit long",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/9/9a/Gull_portrait_ca_usa.jpg"
    ) {
        Row {
            ProvideEmphasis(EmphasisAmbient.current.medium) {
                Text(
                    text = "2017",
                    style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Light)
                )
                Text(
                    text = "2h 30m",
                    style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Light),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewBody() {
    DetailOverview(
        "https://upload.wikimedia.org/wikipedia/commons/9/9a/Gull_portrait_ca_usa.jpg",
        listOf("Adventure", "Drama"),
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
    )
}

@Composable
@Preview(showBackground = true)
fun PreviewWishlistButton() {
    val isWishlisted = remember { mutableStateOf(true) }

    WishlistTextButton(
        isWishlisted = isWishlisted,
        onWishlistToggled = { isWishlisted.value = !isWishlisted.value },
        modifier = Modifier.fillMaxWidth().padding(4.dp)
    )
}

@Composable
@Preview(showBackground = true)
fun PreviewReviewZone() {
    Review(6.3, 1031)
}
