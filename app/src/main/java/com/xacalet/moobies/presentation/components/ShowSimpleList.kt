package com.xacalet.moobies.presentation.components

import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun ShowSimpleList(
    shows: List<SimpleShowListData>,
    onItemClick: (Long) -> Unit
) {
    LazyColumnFor(items = shows) { item ->
        ShowSimpleListItem(item, onItemClick)
    }
}

@Composable
fun ShowSimpleListItem(
    show: SimpleShowListData,
    onClick: (Long) -> Unit = {}
) {
    Column {
        Row(
            modifier = Modifier
                .padding(4.dp)
                .clickable(onClick = { onClick(show.id) }),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CoilImage(
                data = show.imageUrl,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .preferredWidth(54.dp)
                    .preferredHeight(80.dp)
            )
            Column(
                modifier = Modifier
                    .weight(1F)
                    .padding(start = 8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(show.title)
                Text(show.year)
            }
        }
        Divider()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewShowSimpleList() {
    val data = listOf(
        SimpleShowListData(
            1,
            "https://image.tmdb.org/t/p/w92//4BgSWFMW2MJ0dT5metLzsRWO7IJ.jpg",
            "Inglorious Bastards",
            "2017"
        ),
        SimpleShowListData(
            2,
            "https://image.tmdb.org/t/p/w92//4BgSWFMW2MJ0dT5metLzsRWO7IJ.jpg",
            "Kill Bill: Volume 2",
            "2004"
        ),
        SimpleShowListData(
            3,
            "https://image.tmdb.org/t/p/w92//4BgSWFMW2MJ0dT5metLzsRWO7IJ.jpg",
            "Inglorious Basterds",
            "2009"
        ),
    )
    ShowSimpleList(data) {}
}

data class SimpleShowListData(
    val id: Long,
    val imageUrl: String,
    val title: String,
    val year: String
)