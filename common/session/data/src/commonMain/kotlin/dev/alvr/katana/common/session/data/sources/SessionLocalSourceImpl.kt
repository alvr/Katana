package dev.alvr.katana.common.session.data.sources

import androidx.datastore.core.DataStore
import arrow.core.Either
import arrow.core.None
import arrow.core.left
import arrow.core.right
import arrow.core.toOption
import co.touchlab.kermit.Logger
import dev.alvr.katana.common.session.data.entities.Session
import dev.alvr.katana.common.session.domain.failures.SessionFailure
import dev.alvr.katana.common.session.domain.models.AnilistToken
import dev.alvr.katana.core.domain.failures.Failure
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

internal class SessionLocalSourceImpl(
    private val store: DataStore<Session>,
) : SessionLocalSource {
    override val sessionActive = store.data.map { session ->
        @Suppress("USELESS_CAST")
        (session.anilistToken != null && session.sessionActive).right() as Either<Failure, Boolean>
    }.catch { error ->
        Logger.e(LogTag, error) { "Error observing the session, setting the as inactive" }
        emit(SessionFailure.CheckingActiveSession.left())
    }

    override suspend fun clearActiveSession() = Either.catch {
        store.updateData { p -> p.copy(sessionActive = false) }
        Logger.d(LogTag) { "Session cleared" }
    }.mapLeft { error ->
        Logger.e(LogTag, error) { "Error clearing session" }
        SessionFailure.ClearingSession
    }

    override suspend fun deleteAnilistToken() = Either.catch {
        store.updateData { p -> p.copy(anilistToken = null) }
        Logger.d(LogTag) { "Anilist token deleted" }
    }.mapLeft { error ->
        Logger.e(LogTag, error) { "Was not possible to delete the token" }
        SessionFailure.DeletingToken
    }

    override suspend fun getAnilistToken() = store.data
        .map { session -> session.anilistToken.toOption() }
        .catch { error ->
            Logger.e(LogTag, error) { "There was an error reading the token from the preferences" }
            emit(None)
        }.first()

    override suspend fun logout() = Either.catch {
        store.updateData { p -> p.copy(anilistToken = null, sessionActive = false) }
        Logger.d(LogTag) { "Logged out" }
    }.mapLeft { error ->
        Logger.e(LogTag, error) { "Was not possible to logout" }
        SessionFailure.LoggingOut
    }

    override suspend fun saveSession(anilistToken: AnilistToken) = Either.catch {
        store.updateData { p -> p.copy(anilistToken = anilistToken, sessionActive = true) }
        Logger.d(LogTag) { "Token saved: ${anilistToken.token}" }
    }.mapLeft { error ->
        Logger.e(LogTag, error) { "Was not possible to save the token" }
        SessionFailure.SavingSession
    }
}

private const val LogTag = "SessionLocalSource"
