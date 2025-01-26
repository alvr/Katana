package dev.alvr.katana.features.home.data.datastore

import androidx.datastore.core.DataStore
import app.cash.turbine.test
import dev.alvr.katana.core.tests.koinExtension
import dev.alvr.katana.features.home.data.di.corruptedDataStoreNamed
import dev.alvr.katana.features.home.data.di.dataStoreNamed
import dev.alvr.katana.features.home.data.di.deleteDataStoreFiles
import dev.alvr.katana.features.home.data.di.testDataStoreModule
import dev.alvr.katana.features.home.data.entities.HomePreferences
import io.kotest.core.spec.style.FreeSpec
import io.kotest.core.test.TestCase
import io.kotest.matchers.equals.shouldBeEqual
import org.koin.test.KoinTest
import org.koin.test.inject

internal class HomePreferencesDataStoreTest : FreeSpec(), KoinTest {
    private val dataStore by inject<DataStore<HomePreferences>>(dataStoreNamed)
    private val corruptedDataStore by inject<DataStore<HomePreferences>>(corruptedDataStoreNamed)

    init {
        "!initial home preferences should equal to the HomePreferences class" {
            dataStore.data.test {
                awaitItem() shouldBeEqual HomePreferences()
                ensureAllEventsConsumed()
            }
        }

        "!saving a home preferences should return the same values" {
            with(dataStore) {
                updateData { p ->
                    p.copy(
                        welcomeCardVisible = false,
                    )
                }

                data.test {
                    awaitItem() shouldBeEqual HomePreferences(
                        welcomeCardVisible = false,
                    )
                    ensureAllEventsConsumed()
                }
            }
        }

        "!corrupted dataStore should recreate again the file with initial values" {
            corruptedDataStore.data.test {
                awaitItem() shouldBeEqual HomePreferences(welcomeCardVisible = false)
                ensureAllEventsConsumed()
            }
        }
    }

    override suspend fun beforeEach(testCase: TestCase) {
        deleteDataStoreFiles()
    }

    override fun extensions() = listOf(koinExtension(testDataStoreModule()))
}
