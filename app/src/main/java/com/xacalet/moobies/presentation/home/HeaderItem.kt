package com.xacalet.moobies.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import com.xacalet.moobies.presentation.ui.MoobiesTheme
import com.xacalet.moobies.presentation.ui.SecondaryText
import kotlinx.coroutines.flow.MutableStateFlow


@Composable
fun HeaderItem(
    modifier: Modifier = Modifier,
    itemModifier: Modifier = Modifier,
    data: HeaderItemData,
    onShowClick: (Long) -> Unit,
    onWishlistButtonClick: (Long) -> Unit
) {
    Box(modifier) {
        Image(
            painter = rememberImagePainter(data.backdropUrl),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 42.dp),
        )
        Row(
            modifier = itemModifier
                .fillMaxWidth()
                .padding(start = 20.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { onShowClick(data.id) }
                .align(Alignment.BottomStart)
        ) {
            Box(
                modifier = Modifier
                    .height(180.dp)
                    .width(120.dp)
            ) {
                Image(
                    painter = rememberImagePainter(data.posterUrl),
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
                WishlistButton(
                    modifier = Modifier.align(Alignment.TopStart),
                    isWishListed = data.isWishlisted.collectAsState().value,
                    onClick = { onWishlistButtonClick(data.id) }
                )
            }
            Column(
                modifier = Modifier
                    .align(Alignment.Bottom)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                CompositionLocalProvider(LocalTextStyle provides MaterialTheme.typography.caption) {
                    Text(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        text = data.title ?: "",
                        maxLines = 1,
                        color = MaterialTheme.colors.onSurface,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        text = data.subtitle ?: "",
                        style = LocalTextStyle.current.copy(fontSize = 10.sp),
                        color = SecondaryText,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@ExperimentalPagerApi
@Composable
@Preview(showBackground = true)
fun HeaderPreview() {
    val isWishlistedFlows = listOf(
        remember { MutableStateFlow(false) },
        remember { MutableStateFlow(false) }
    )
    val items = listOf(
        HeaderItemData(
            id = 100L,
            title = "Scott Pilgrim vs. the World",
            subtitle = "Official Trailer",
            posterUrl = "https://es.web.img3.acsta.net/pictures/bzp/01/136370.jpg",
            backdropUrl = "https://grovewatch.com/wp-content/uploads/2018/10/scott-pilgrim-vs-the-world-the-movie-head-e1538504647626-900x371.png",
            isWishlisted = isWishlistedFlows[0]
        ),
        HeaderItemData(
            id = 200L,
            title = "Hot Fuzz",
            subtitle = "Official Trailer",
            posterUrl = "https://images-na.ssl-images-amazon.com/images/I/81lbf9-U3FL._RI_.jpg",
            backdropUrl = "https://www.nme.com/wp-content/uploads/2018/12/HOT_FUZZ_2000-696x442.jpg",
            isWishlisted = isWishlistedFlows[1]
        )
    )
    MoobiesTheme {
        val pagerState = rememberPagerState(
            pageCount = 2,
            initialOffscreenLimit = 2,
        )

        HorizontalPager(state = pagerState) { page ->
            HeaderItem(
                modifier = Modifier.height(240.dp),
                itemModifier = Modifier
                    .offset(x = ((-128).dp * calculateCurrentOffsetForPage(page))),
                data = items[page],
                onShowClick = {},
                onWishlistButtonClick = {
                    isWishlistedFlows[page].value = !isWishlistedFlows[page].value
                }
            )
        }
    }
}
