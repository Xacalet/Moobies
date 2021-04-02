package com.xacalet.moobies.presentation.moviedetails.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xacalet.moobies.R
import com.xacalet.moobies.presentation.ui.Blue400
import com.xacalet.moobies.presentation.ui.MoobiesTheme
import com.xacalet.moobies.presentation.ui.Yellow600
import java.text.NumberFormat
import java.util.*
import kotlin.random.Random

@Composable
fun RatingSection(
    voteAverage: Double,
    voteCount: Int,
    userRating: State<Int?>,
    onUserRatingClick: () -> Unit = {}
) {
    Surface {
        Row {
            //Popular rating
            StarredRatingItem(
                modifier = Modifier.weight(1F),
                rating = voteAverage.toFloat(),
                starColor = Yellow600,
                subtitle = NumberFormat.getInstance().format(voteCount)
            )
            userRating.value?.let { stars ->
                StarredRatingItem(
                    modifier = Modifier.weight(1F),
                    onClick = onUserRatingClick,
                    rating = stars.toFloat(),
                    starColor = Blue400,
                    subtitle = stringResource(R.string.you),
                    ratingFormatter = { rating ->
                        rating.toInt().toString()
                    }
                )
            } ?: PendingUserRatingItem(
                modifier = Modifier.weight(1F),
                onClick = onUserRatingClick
            )
            //Review
            Box(Modifier.weight(1F))
        }
    }
}

@Composable
fun StarredRatingItem(
    modifier: Modifier = Modifier,
    rating: Float,
    onClick: () -> Unit = {},
    starColor: Color,
    subtitle: String,
    ratingFormatter: (Float) -> String = { value -> value.toString() },
) {
    Column(
        modifier = modifier.clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            imageVector = Icons.Filled.Star,
            contentDescription = null,
            colorFilter = ColorFilter.tint(starColor),
            modifier = Modifier.size(32.dp)
        )
        Text(
            buildAnnotatedString {
                withStyle(SpanStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)) {
                    append(ratingFormatter(rating))
                }
                withStyle(SpanStyle(fontSize = 16.sp)) {
                    append("/10")
                }
            }
        )
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(subtitle, style = MaterialTheme.typography.body1)
        }
    }
}

@Composable
fun PendingUserRatingItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier.clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            imageVector = Icons.Outlined.StarOutline,
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface),
            modifier = Modifier.size(32.dp)
        )
        Text(
            text = stringResource(R.string.rate_this).toUpperCase(Locale.getDefault()),
            style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
        )
    }
}

@Composable
@Preview(name = "With user rating", showBackground = true)
fun PreviewReviewZoneWithUserRating() {
    val stars: MutableState<Int?> = remember { mutableStateOf(6) }
    val onClick = {
        stars.value = if (stars.value == null) Random.nextInt(1, 10) else null
    }
    RatingSection(6.3, 1031, stars, onClick)
}

@Composable
@Preview(
    name = "Without user rating",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
fun PreviewReviewZoneWithoutUserRating() {
    MoobiesTheme {
        val stars: MutableState<Int?> = remember { mutableStateOf(null) }
        val onClick = {
            stars.value = if (stars.value == null) Random.nextInt(1, 10) else null
        }
        RatingSection(6.3, 1031, stars, onClick)
    }
}
