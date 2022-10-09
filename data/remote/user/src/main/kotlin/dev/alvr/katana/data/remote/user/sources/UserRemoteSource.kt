package dev.alvr.katana.data.remote.user.sources

import arrow.core.Either
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.apollographql.apollo3.cache.normalized.fetchPolicy
import dev.alvr.katana.data.remote.base.failures.CommonRemoteFailure
import dev.alvr.katana.data.remote.user.UserIdQuery
import dev.alvr.katana.data.remote.user.mappers.responses.invoke
import dev.alvr.katana.domain.base.failures.Failure
import dev.alvr.katana.domain.user.failures.UserFailure
import javax.inject.Inject

internal class UserRemoteSource @Inject constructor(
    private val client: ApolloClient,
) {
    suspend fun getUserId() = Either.catch(
        f = { client.query(UserIdQuery()).fetchPolicy(FetchPolicy.CacheOnly).execute().data() },
        fe = { UserFailure.UserIdFailure },
    )

    suspend fun saveUserId() = Either.catch(
        f = { client.query(UserIdQuery()).execute() },
        fe = { error ->
            when (CommonRemoteFailure(error)) {
                CommonRemoteFailure.NetworkFailure -> UserFailure.FetchingFailure
                CommonRemoteFailure.ResponseFailure -> UserFailure.SavingFailure
                CommonRemoteFailure.CacheFailure -> Failure.Unknown
                CommonRemoteFailure.UnknownFailure -> Failure.Unknown
            }
        },
    ).void()
}