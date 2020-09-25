package com.xacalet.moobies.presentation.userrating

import androidx.compose.foundation.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.ui.tooling.preview.Preview
import com.xacalet.moobies.presentation.ui.MoobiesTheme

@Composable
fun UserRatingScreen(
    data: UserRatingUiModel?
) {
    MoobiesTheme {
        Surface {
            if (data == null) {
                // TODO: Set content to manage user rating
                Box(modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)) {
                    CircularProgressIndicator()
                }
            } else {
                // TODO: Set show poster with blur effect as background image
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewUserRatingScreen() {
    UserRatingScreen(UserRatingUiModel(1, 7, ""))
}
