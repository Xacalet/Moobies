package com.xacalet.moobies.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xacalet.moobies.presentation.ui.MoobiesTheme

@Composable
fun SectionCard(
    modifier: Modifier = Modifier,
    header: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(0.dp)
    ) {
        Column(
            Modifier.fillMaxWidth()
        ) {
            Box(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                header()
            }
            content()
        }
    }
}

@Composable
@Preview
fun SectionCardPreview() {
    MoobiesTheme {
        SectionCard(header = { CardTitle("A Section") }) {
            Box(modifier = Modifier.height(200.dp))
        }
    }
}
