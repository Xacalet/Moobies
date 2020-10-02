package com.xacalet.moobies.presentation.userrating

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.platform.setContent
import com.xacalet.moobies.presentation.ui.MoobiesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserRatingActivity : AppCompatActivity() {

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val id = intent.getLongExtra(EXTRA_ID, -1L)

        setContent {
            MoobiesTheme {
                UserRatingScreen(
                    showId = id,
                    onClose = { finish() }
                )
            }
        }
    }

    companion object {
        const val EXTRA_ID = "ID"
    }
}
