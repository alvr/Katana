package dev.alvr.katana.features.lists.domain.usecases

import app.cash.turbine.test
import arrow.core.left
import arrow.core.right
import dev.alvr.katana.core.common.coroutines.KatanaDispatcher
import dev.alvr.katana.core.domain.usecases.invoke
import dev.alvr.katana.core.tests.shouldBeLeft
import dev.alvr.katana.core.tests.shouldBeRight
import dev.alvr.katana.features.lists.domain.failures.ListsFailure
import dev.alvr.katana.features.lists.domain.models.MediaCollection
import dev.alvr.katana.features.lists.domain.models.entries.MediaEntry
import dev.alvr.katana.features.lists.domain.repositories.ListsRepository
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import dev.mokkery.verify
import io.kotest.core.spec.style.FreeSpec
import io.kotest.core.test.TestCase
import kotlinx.coroutines.flow.flowOf
import org.koin.test.KoinTest
import org.koin.test.inject

internal class ObserveMangaListUseCaseTest : FreeSpec(), KoinTest {
    private val dispatcher by inject<KatanaDispatcher>()
    private val repo = mock<ListsRepository>()

    private lateinit var useCase: ObserveMangaListUseCase

    init {
        "successfully observe the manga lists" {
            every { repo.mangaCollection } returns flowOf(
                MediaCollection<MediaEntry.Manga>(emptyList()).right(),
            )

            useCase()

            useCase.flow.test {
                awaitItem().shouldBeRight(MediaCollection(emptyList()))
                ensureAllEventsConsumed()
            }

            verify { repo.mangaCollection }
        }

        "failure observe the manga lists" {
            every { repo.mangaCollection } returns flowOf(
                ListsFailure.GetMediaCollection.left(),
            )

            useCase()

            useCase.flow.test {
                awaitItem().shouldBeLeft(ListsFailure.GetMediaCollection)
                ensureAllEventsConsumed()
            }

            verify { repo.mangaCollection }
        }
    }

    override suspend fun beforeEach(testCase: TestCase) {
        useCase = ObserveMangaListUseCase(dispatcher, repo)
    }
}
