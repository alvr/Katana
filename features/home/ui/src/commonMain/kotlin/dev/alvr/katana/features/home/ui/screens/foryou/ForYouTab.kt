package dev.alvr.katana.features.home.ui.screens.foryou

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.alvr.katana.features.home.ui.screens.foryou.sections.Popular
import dev.alvr.katana.features.home.ui.screens.foryou.sections.Reading
import dev.alvr.katana.features.home.ui.screens.foryou.sections.Trending
import dev.alvr.katana.features.home.ui.screens.foryou.sections.Upcoming
import dev.alvr.katana.features.home.ui.screens.foryou.sections.Watching
import dev.alvr.katana.features.home.ui.screens.foryou.sections.WelcomeCard
import dev.alvr.katana.features.home.ui.viewmodel.HomeIntent
import dev.alvr.katana.features.home.ui.viewmodel.HomeState

@Composable
internal fun ForYouTabContent(
    sessionActive: Boolean,
    uiState: HomeState.ForYouTabState,
    onIntent: (HomeIntent) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        if (!sessionActive && uiState.showWelcomeCard) {
            item(key = "welcome_card") {
                WelcomeCard(
                    modifier = Modifier.animateItem(),
                    onIntent = onIntent,
                )
            }
        }

        if (sessionActive) {
            item(key = "watching") {
                Watching(
                    modifier = Modifier.animateItem(),
                    onIntent = onIntent,
                )
            }
            item(key = "reading") {
                Reading(
                    modifier = Modifier.animateItem(),
                    onIntent = onIntent,
                )
            }
        }

        item(key = "trending") {
            Trending(
                modifier = Modifier.animateItem(),
                onIntent = onIntent,
            )
        }
        item(key = "popular") {
            Popular(
                modifier = Modifier.animateItem(),
                onIntent = onIntent,
            )
        }
        item(key = "upcoming") {
            Upcoming(
                modifier = Modifier.animateItem(),
                onIntent = onIntent,
            )
        }
    }
}
