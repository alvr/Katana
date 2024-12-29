package dev.alvr.katana.common.user.data.sources

import io.kotest.core.spec.style.FreeSpec
import io.kotest.core.test.TestCase

internal class UserLocalSourceTest : FreeSpec() {
    private lateinit var source: UserLocalSource

    init {
        "test" {
            // TODO
        }
    }

    override suspend fun beforeEach(testCase: TestCase) {
        source = UserLocalSourceImpl()
    }
}
