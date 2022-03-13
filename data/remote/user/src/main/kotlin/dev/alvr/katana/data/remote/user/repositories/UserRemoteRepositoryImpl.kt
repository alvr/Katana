package dev.alvr.katana.data.remote.user.repositories

import com.apollographql.apollo3.ApolloClient
import dev.alvr.katana.data.remote.user.UserIdQuery
import dev.alvr.katana.data.remote.user.mappers.responses.invoke
import dev.alvr.katana.domain.user.models.UserId
import dev.alvr.katana.domain.user.repositories.UserRemoteRepository
import javax.inject.Inject

internal class UserRemoteRepositoryImpl @Inject constructor(
    private val client: ApolloClient
) : UserRemoteRepository {
    override suspend fun getUserId(): UserId =
        client.query(UserIdQuery()).execute().data()

    override suspend fun saveUserId() {
        client.query(UserIdQuery()).execute()
    }
}
