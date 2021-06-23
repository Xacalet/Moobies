package com.xacalet.moobies.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.coil.rememberCoilPainter
import com.xacalet.moobies.presentation.ui.MoobiesTheme
import com.xacalet.moobies.presentation.ui.SecondaryText
import com.xacalet.moobies.presentation.ui.Yellow600
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ShowItem(
    data: ShowItemData,
    onWishlistButtonClicked: (Long) -> Unit,
    onClick: (Long) -> Unit
) {
    Card(
        modifier = Modifier
            .width(140.dp)
            .height(300.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp
    ) {
        Box(
            modifier = Modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { onClick(data.id) }
            )
        ) {
            Column {
                CompositionLocalProvider(LocalTextStyle provides MaterialTheme.typography.caption) {
                    Image(
                        painter = rememberCoilPainter(data.posterUrl),
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth(),
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    Row(
                        modifier = Modifier.padding(start = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.size(18.dp),
                            imageVector = Icons.Rounded.Star,
                            tint = Yellow600,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                        Text("${data.score}")
                    }
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        text = data.title ?: "",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        text = data.subtitle ?: "",
                        style = LocalTextStyle.current.copy(fontSize = 10.sp),
                        color = SecondaryText
                    )
                }
            }
            WishlistButton(
                isWishListed = data.isWishlisted.collectAsState().value,
                onClick = { onWishlistButtonClicked(data.id) }
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ShowItemPreview() {
    val isWishlisted = remember { MutableStateFlow(false) }
    val data = ShowItemData(
        id = 100,
        title = "Scott Pilgrim vs. the World",
        subtitle = "2010 A 1h 52m",
        posterUrl = "https://es.web.img3.acsta.net/pictures/bzp/01/136370.jpg",
        score = 6.9,
        isWishlisted = isWishlisted
    )
    MoobiesTheme {
        Surface {
            ShowItem(
                data = data,
                onWishlistButtonClicked = { isWishlisted.value = !isWishlisted.value },
                onClick = {}
            )
        }
    }
}

data class ShowItemData(
    val id: Long,
    val title: String?,
    val subtitle: String?,
    val score: Double?,
    val posterUrl: String?,
    val isWishlisted: StateFlow<Boolean>
)
