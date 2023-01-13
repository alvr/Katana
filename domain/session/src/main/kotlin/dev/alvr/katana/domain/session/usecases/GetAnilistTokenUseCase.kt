package dev.alvr.katana.domain.session.usecases

import dev.alvr.katana.domain.base.usecases.OptionUseCase
import dev.alvr.katana.domain.session.models.AnilistToken
import dev.alvr.katana.domain.session.repositories.SessionRepository

class GetAnilistTokenUseCase(
    private val repository: SessionRepository,
) : OptionUseCase<Unit, AnilistToken> {
    override suspend fun invoke(params: Unit) = repository.getAnilistToken()
}
