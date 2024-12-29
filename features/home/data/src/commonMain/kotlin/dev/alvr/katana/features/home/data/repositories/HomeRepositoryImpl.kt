package dev.alvr.katana.features.home.data.repositories

import dev.alvr.katana.features.home.data.sources.HomeLocalSource
import dev.alvr.katana.features.home.data.sources.HomeRemoteSource
import dev.alvr.katana.features.home.domain.repositories.HomeRepository

@Suppress("UnusedPrivateProperty")
internal class HomeRepositoryImpl(
    private val localSource: HomeLocalSource,
    @Suppress("Unused") private val remoteSource: HomeRemoteSource,
) : HomeRepository {
    override val welcomeCardVisible = localSource.welcomeCardVisible

    override suspend fun hideWelcomeCard() = localSource.hideWelcomeCard()
}
