package dev.alvr.katana.common.session.data.mocks

import dev.alvr.katana.common.session.data.entities.Session
import dev.alvr.katana.common.session.domain.models.AnilistToken
import io.kotest.property.Arb
import io.kotest.property.arbitrary.boolean
import io.kotest.property.arbitrary.next

internal val anilistTokenMock = AnilistToken("TOKEN")

internal val sessionMock = Session(
    anilistToken = anilistTokenMock,
    sessionActive = Arb.boolean().next(),
)
