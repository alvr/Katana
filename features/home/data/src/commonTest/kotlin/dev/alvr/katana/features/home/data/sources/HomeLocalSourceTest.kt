package dev.alvr.katana.features.home.data.sources

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import app.cash.turbine.test
import dev.alvr.katana.core.tests.shouldBeLeft
import dev.alvr.katana.core.tests.shouldBeRight
import dev.alvr.katana.features.home.data.entities.HomePreferences
import dev.alvr.katana.features.home.domain.failures.HomeFailure
import dev.mokkery.answering.returns
import dev.mokkery.answering.throws
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verify
import dev.mokkery.verifySuspend
import io.kotest.core.spec.style.FreeSpec
import io.kotest.core.test.TestCase
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf

internal class HomeLocalSourceTest : FreeSpec() {
    private val store = mock<DataStore<HomePreferences>> {
        every { data } returns emptyFlow()
    }

    private lateinit var source: HomeLocalSource

    init {
        "successful" - {
            every { store.data } returns flowOf(
                HomePreferences(welcomeCardVisible = false),
                HomePreferences(welcomeCardVisible = false),
                HomePreferences(welcomeCardVisible = true),
                HomePreferences(welcomeCardVisible = true),
            )

            "getting the welcome card visibility" {
                source.welcomeCardVisible.test {
                    awaitItem().shouldBeRight(false)
                    awaitItem().shouldBeRight(true)
                    awaitComplete()
                }

                verify { store.data }
            }

            "hiding the welcome card" {
                everySuspend { store.updateData(any()) } returns HomePreferences()
                source.hideWelcomeCard().shouldBeRight()
                verifySuspend { store.updateData(any()) }
            }
        }

        "failure" - {
            "hiding the welcome card fails AND it's a common Exception" {
                everySuspend { store.updateData(any()) } throws Exception()
                source.hideWelcomeCard().shouldBeLeft(HomeFailure.HidingWelcomeCard)
                verifySuspend { store.updateData(any()) }
            }

            "hiding the welcome card fails AND it's a writing Exception" {
                everySuspend { store.updateData(any()) } throws IOException("Oops.")
                source.hideWelcomeCard().shouldBeLeft(HomeFailure.HidingWelcomeCard)
                verifySuspend { store.updateData(any()) }
            }
        }
    }

    override suspend fun beforeEach(testCase: TestCase) {
        source = HomeLocalSourceImpl(store)
    }
}
