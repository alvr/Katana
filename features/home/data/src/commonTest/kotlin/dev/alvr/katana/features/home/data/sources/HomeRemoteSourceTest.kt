package dev.alvr.katana.features.home.data.sources

import io.kotest.core.spec.style.FreeSpec
import io.kotest.core.test.TestCase

internal class HomeRemoteSourceTest : FreeSpec() {
    private lateinit var remoteSource: HomeRemoteSource

    init {
        "test" {
            // TODO
        }
    }

    override suspend fun beforeEach(testCase: TestCase) {
        remoteSource = HomeRemoteSourceImpl()
    }
}
