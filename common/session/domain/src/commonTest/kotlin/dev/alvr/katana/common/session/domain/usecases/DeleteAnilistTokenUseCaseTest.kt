package dev.alvr.katana.common.session.domain.usecases

import arrow.core.left
import arrow.core.right
import dev.alvr.katana.common.session.domain.failures.SessionFailure
import dev.alvr.katana.common.session.domain.repositories.SessionRepository
import dev.alvr.katana.core.common.coroutines.KatanaDispatcher
import dev.alvr.katana.core.domain.failures.Failure
import dev.alvr.katana.core.domain.usecases.invoke
import dev.alvr.katana.core.tests.di.coreTestsModule
import dev.alvr.katana.core.tests.koinExtension
import dev.alvr.katana.core.tests.shouldBeLeft
import dev.alvr.katana.core.tests.shouldBeRight
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import io.kotest.core.spec.style.FreeSpec
import io.kotest.core.test.TestCase
import org.koin.test.KoinTest
import org.koin.test.inject

internal class DeleteAnilistTokenUseCaseTest : FreeSpec(), KoinTest {
    private val dispatcher by inject<KatanaDispatcher>()
    private val repo = mock<SessionRepository>()

    private lateinit var useCase: DeleteAnilistTokenUseCase

    init {
        "successfully deleting the token" {
            everySuspend { repo.deleteAnilistToken() } returns Unit.right()
            useCase().shouldBeRight(Unit)
            verifySuspend { repo.deleteAnilistToken() }
        }

        listOf(
            SessionFailure.DeletingToken to SessionFailure.DeletingToken.left(),
            Failure.Unknown to Failure.Unknown.left(),
        ).forEach { (expected, failure) ->
            "failure deleting the token ($expected)" {
                everySuspend { repo.deleteAnilistToken() } returns failure
                useCase().shouldBeLeft(expected)
                verifySuspend { repo.deleteAnilistToken() }
            }
        }
    }

    override suspend fun beforeEach(testCase: TestCase) {
        useCase = DeleteAnilistTokenUseCase(dispatcher, repo)
    }

    override fun extensions() = listOf(koinExtension(coreTestsModule))
}
