package com.xacalet.moobies.presentation.moviedetails.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.EmphasisAmbient
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideEmphasis
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.runtime.*
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.annotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.ui.tooling.preview.Preview
import com.xacalet.moobies.R
import com.xacalet.moobies.presentation.ui.Blue400
import com.xacalet.moobies.presentation.ui.Yellow600
import java.text.NumberFormat
import kotlin.random.Random

@Composable
fun RatingSection(
    voteAverage: Double,
    voteCount: Int,
    userRating: State<Byte?>,
    onUserRatingClick: () -> Unit = {}
) {
    Row {
        //Popular rating
        RatingItemButton(
            modifier = Modifier.weight(1F),
            onClick = { /* Does nothing */ }
        ) {
            StarredRatingItem(
                modifier = Modifier.weight(1F),
                rating = voteAverage.toFloat(),
                starColor = Yellow600,
                subtitle = NumberFormat.getInstance().format(voteCount)
            )
        }
        RatingItemButton(
            modifier = Modifier.weight(1F),
            onClick = onUserRatingClick
        ) {
            userRating.value?.let { stars ->
                StarredRatingItem(
                    modifier = Modifier.weight(1F),
                    rating = stars.toFloat(),
                    starColor = Blue400,
                    subtitle = stringResource(R.string.you),
                    ratingFormatter = { rating ->
                        rating.toInt().toString()
                    }
                )
            } ?: PendingUserRatingItem()
        }
        //Review
        Box(Modifier.weight(1F))
    }
}

@Composable
fun RatingItemButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        backgroundColor = Color.Transparent,
        shape = RoundedCornerShape(0.dp),
        elevation = 0.dp,
        content = content
    )
}

@Composable
fun StarredRatingItem(
    modifier: Modifier = Modifier,
    rating: Float,
    starColor: Color,
    subtitle: String,
    ratingFormatter: (Float) -> String = { value -> value.toString() },
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            asset = Icons.Filled.Star,
            colorFilter = ColorFilter.tint(starColor),
            modifier = Modifier.size(32.dp)
        )
        Text(
            annotatedString {
                withStyle(SpanStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)) {
                    append(ratingFormatter(rating))
                }
                withStyle(SpanStyle(fontSize = 16.sp)) {
                    append("/10")
                }
            }
        )
        ProvideEmphasis(EmphasisAmbient.current.medium) {
            Text(subtitle, style = MaterialTheme.typography.body1)
        }
    }
}

@Composable
fun PendingUserRatingItem() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            asset = Icons.Outlined.StarOutline,
            colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface),
            modifier = Modifier.size(32.dp)
        )
        Text(
            text = stringResource(R.string.rate_this).toUpperCase(),
            style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
        )
    }
}

@Composable
@Preview(name = "With user rating", showBackground = true)
fun PreviewReviewZoneWithUserRating() {
    val stars: MutableState<Byte?> = remember { mutableStateOf(6) }
    val onClick = {
        stars.value = if (stars.value == null) Random.nextInt(1, 10).toByte() else null
    }
    RatingSection(6.3, 1031, stars, onClick)
}

@Composable
@Preview(name = "Without user rating", showBackground = true)
fun PreviewReviewZoneWithoutUserRating() {
    val stars: MutableState<Byte?> = remember { mutableStateOf(null) }
    val onClick = {
        stars.value = if (stars.value == null) Random.nextInt(1, 10).toByte() else null
    }
    RatingSection(6.3, 1031, stars, onClick)
}