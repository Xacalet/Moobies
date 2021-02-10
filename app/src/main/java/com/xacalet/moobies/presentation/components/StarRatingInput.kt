package com.xacalet.moobies.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xacalet.moobies.presentation.ui.Blue600

@Composable
fun StarRatingInput(
    modifier: Modifier = Modifier,
    rating: Int?,
    onRatingChanged: (Int?) -> Unit,
    starCount: Int = 10,
) = Row(
    modifier = modifier.height(40.dp),
    verticalAlignment = Alignment.CenterVertically
) {
    (1..starCount).forEach { index ->
        val isHighlighted = index <= rating ?: 0
        Image(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            colorFilter = ColorFilter.tint(if (isHighlighted) Blue600 else Color.Gray),
            modifier = Modifier
                .clickable { onRatingChanged(index) }
                .weight(1F)
                .size(if (isHighlighted) 32.dp else 24.dp)
                .semantics { testTag = "star_${index}_${if (isHighlighted) "on" else "off"}" }
        )
    }
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun PreviewStarRatingInput() {
    val (rating, onRatingChanged) = remember { mutableStateOf<Int?>(5) }
    StarRatingInput(Modifier, rating, onRatingChanged)
}
