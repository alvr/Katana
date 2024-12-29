package dev.alvr.katana.features.home.domain.repositories

import arrow.core.Either
import dev.alvr.katana.core.domain.failures.Failure
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    val welcomeCardVisible: Flow<Either<Failure, Boolean>>

    suspend fun hideWelcomeCard(): Either<Failure, Unit>
}
