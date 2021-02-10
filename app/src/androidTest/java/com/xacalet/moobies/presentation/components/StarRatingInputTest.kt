package com.xacalet.moobies.presentation.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test

class StarRatingInputTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun ratingNull_allStarsTurnedOff() {
        val starCount = 9
        val rating = null

        composeTestRule.setContent {
            StarRatingInput(
                rating = rating,
                starCount = starCount,
                onRatingChanged = {}
            )
        }

        (1..starCount).forEach { index ->
            composeTestRule.onNodeWithTag("star_${index}_off").assertIsDisplayed()
        }
    }

    @Test
    fun ratingIsN_firstNStarsAreTurnedOn() {
        val starCount = 9
        val rating = 5

        composeTestRule.setContent {
            StarRatingInput(
                rating = rating,
                starCount = starCount,
                onRatingChanged = {}
            )
        }

        (1..rating).forEach {
            composeTestRule.onNodeWithTag("star_${it}_on").assertIsDisplayed()
        }
        (rating + 1..starCount).forEach {
            composeTestRule.onNodeWithTag("star_${it}_off").assertIsDisplayed()
        }
    }

    @Test
    @SuppressWarnings("unused")
    fun clickingOnStarN_turnsOnFirstNStarsAndTriggersOnRatingChanged() {
        val onRatingChanged = mockk<(Int?) -> Unit>(relaxed = true)

        composeTestRule.setContent {
            StarRatingInput(
                rating = null,
                starCount = 9,
                onRatingChanged = onRatingChanged
            )
        }
        val newRating = 5
        composeTestRule.onNodeWithTag("star_${newRating}_off").performClick()
        verify { onRatingChanged.invoke(newRating) }
    }
}
