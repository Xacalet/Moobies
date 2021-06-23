package com.xacalet.moobies.presentation.home

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xacalet.moobies.presentation.ui.Yellow600

@Composable
fun WishlistButton(
    modifier: Modifier = Modifier,
    isWishListed: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor by animateColorAsState(
        if (isWishListed) Yellow600 else Color.Black.copy(alpha = .45f)
    )
    Button(
        onClick = onClick,
        modifier = modifier
            .height(50.dp)
            .width(40.dp)
            .drawBehind { wishlistButtonShadow() },
        elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp),
        shape = bookmarkShape(),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor
        ),
        contentPadding = PaddingValues(bottom = 10.dp)
    ) {
        Crossfade(isWishListed) {
            Icon(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxSize(),
                imageVector = if (it) Icons.Rounded.Done else Icons.Rounded.Add,
                tint = if (it) Color.Black else Color.White,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun bookmarkShape() = GenericShape { size, _ ->
    moveTo(0f, 0f)
    lineTo(size.width, 0f)
    lineTo(size.width, size.height)
    lineTo(size.width / 2f, size.height - (size.width / 4f))
    lineTo(0f, size.height)
}

private fun DrawScope.wishlistButtonShadow() {
    val strokeWidth = 2.dp.toPx()
    translate(top = -1f) {
        drawPath(
            color = androidx.compose.ui.graphics.Color.Black.copy(alpha = 0.75f),
            path = Path().apply {
                moveTo(1f, size.height)
                lineTo(1f, size.height + strokeWidth)
                lineTo(size.width / 2f, size.height - (size.width / 4f) + strokeWidth)
                lineTo(size.width, size.height + strokeWidth)
                lineTo(size.width - 1f, size.height)
                lineTo(size.width / 2f, size.height - (size.width / 4f))
                close()
            })
    }
}

@Composable
@Preview
fun WishlistButtonPreview() {
    val isWishListedState = remember { mutableStateOf(true) }
    Box(
        modifier = Modifier
            .size(128.dp)
            .background(Color.Red),
        contentAlignment = Alignment.Center
    ) {
        WishlistButton(Modifier, isWishListedState.value) {
            isWishListedState.value = !isWishListedState.value
        }
    }
}
