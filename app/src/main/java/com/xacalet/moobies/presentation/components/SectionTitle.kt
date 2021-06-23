package com.xacalet.moobies.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xacalet.moobies.presentation.ui.MoobiesTheme
import com.xacalet.moobies.presentation.ui.Yellow600

@Composable
fun CardTitle(text: String) {
    Row(
        modifier = Modifier.wrapContentSize(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .height(22.dp)
                .width(4.dp)
                .clip(RoundedCornerShape(50))
                .background(Yellow600)
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.h6
        )
    }
}

@Composable
fun SectionTitle(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.h4
    )
}

@Composable
@Preview
fun CardTitlePreview() {
    MoobiesTheme {
        Surface {
            CardTitle("Últimas noticias")
        }
    }
}

@Composable
@Preview
fun SectionTitlePreview() {
    MoobiesTheme {
        Surface {
            SectionTitle(text = "Últimas noticias")
        }
    }
}

@Composable
@Preview
fun CardPreview() {
    MoobiesTheme {
        Surface {
            SectionTitle(text = "Últimas noticias")
        }
    }
}
