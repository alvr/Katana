package dev.alvr.katana.features.home.data.sources

import arrow.core.Either
import dev.alvr.katana.core.domain.failures.Failure
import kotlinx.coroutines.flow.Flow

internal interface HomeLocalSource {
    val welcomeCardVisible: Flow<Either<Failure, Boolean>>

    suspend fun hideWelcomeCard(): Either<Failure, Unit>
}
