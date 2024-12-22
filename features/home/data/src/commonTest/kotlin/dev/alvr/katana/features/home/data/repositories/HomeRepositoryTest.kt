package dev.alvr.katana.features.home.data.repositories

import dev.alvr.katana.features.home.data.sources.HomeLocalSource
import dev.alvr.katana.features.home.data.sources.HomeRemoteSource
import dev.alvr.katana.features.home.domain.repositories.HomeRepository
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import io.kotest.core.spec.style.FreeSpec
import io.kotest.core.test.TestCase
import kotlinx.coroutines.flow.emptyFlow

internal class HomeRepositoryTest : FreeSpec() {
    private val localSource = mock<HomeLocalSource> {
        every { welcomeCardVisible } returns emptyFlow()
    }
    private val remoteSource = mock<HomeRemoteSource>()

    private lateinit var repository: HomeRepository

    init {
        "test" {
            // TODO
        }
    }

    override suspend fun beforeEach(testCase: TestCase) {
        repository = HomeRepositoryImpl(localSource, remoteSource)
    }
}
