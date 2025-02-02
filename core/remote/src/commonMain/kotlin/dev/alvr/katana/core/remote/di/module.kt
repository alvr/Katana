package dev.alvr.katana.core.remote.di

import arrow.core.None
import arrow.core.Option
import co.touchlab.kermit.Logger
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.http.HttpRequest
import com.apollographql.apollo.api.http.HttpResponse
import com.apollographql.apollo.cache.normalized.FetchPolicy
import com.apollographql.apollo.cache.normalized.api.CacheKey
import com.apollographql.apollo.cache.normalized.api.CacheKeyGenerator
import com.apollographql.apollo.cache.normalized.api.CacheKeyGeneratorContext
import com.apollographql.apollo.cache.normalized.fetchPolicy
import com.apollographql.apollo.cache.normalized.normalizedCache
import com.apollographql.apollo.interceptor.ApolloInterceptor
import com.apollographql.apollo.network.http.HttpInterceptor
import com.apollographql.apollo.network.http.HttpInterceptorChain
import com.apollographql.apollo.network.http.LoggingInterceptor
import dev.alvr.katana.common.session.domain.models.AnilistToken
import dev.alvr.katana.common.session.domain.usecases.DeleteAnilistTokenUseCase
import dev.alvr.katana.common.session.domain.usecases.GetAnilistTokenUseCase
import dev.alvr.katana.core.common.KatanaBuildConfig
import dev.alvr.katana.core.domain.usecases.invoke
import dev.alvr.katana.core.remote.interceptors.ReloadInterceptor
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.core.scope.ScopeCallback
import org.koin.dsl.bind
import org.koin.dsl.module

internal expect fun apolloDatabaseModule(): Module

internal expect fun ApolloClient.Builder.sentryInterceptor(): ApolloClient.Builder

private enum class Interceptor {
    GET_TOKEN,
    DELETE_TOKEN,
    LOGGING,
}

private val apolloClientModule = module {
    single {
        val cacheKeyGenerator = object : CacheKeyGenerator {
            override fun cacheKeyForObject(
                obj: Map<String, Any?>,
                context: CacheKeyGeneratorContext,
            ): CacheKey? = if (
                obj[CACHE_ID_KEY] != null &&
                obj[CACHE_TYPE_KEY] != null
            ) {
                CacheKey(obj[CACHE_TYPE_KEY].toString(), obj[CACHE_ID_KEY].toString())
            } else {
                null
            }
        }

        ApolloClient.Builder()
            .serverUrl(ANILIST_BASE_URL)
            .addHttpInterceptor(get(named(Interceptor.GET_TOKEN)))
            .addHttpInterceptor(get(named(Interceptor.DELETE_TOKEN)))
            .addHttpInterceptor(get(named(Interceptor.LOGGING)))
            .sentryInterceptor()
            .fetchPolicy(FetchPolicy.CacheAndNetwork)
            .normalizedCache(
                normalizedCacheFactory = get(),
                cacheKeyGenerator = cacheKeyGenerator,
                writeToCacheAsynchronously = true,
            )
            .build()
    }
}

private val apolloInterceptorsModule = module {
    single<HttpInterceptor>(named(Interceptor.GET_TOKEN)) {
        var token: Option<AnilistToken> = None
        val useCase = get<GetAnilistTokenUseCase>()

        registerCallback(object : ScopeCallback {
            override fun onScopeClose(scope: Scope) {
                token = None
            }
        })

        object : HttpInterceptor {
            override suspend fun intercept(request: HttpRequest, chain: HttpInterceptorChain): HttpResponse {
                val bearer = token.fold(
                    ifEmpty = { useCase().also { token = it }.getOrNull() },
                    ifSome = { it },
                )?.token

                return request.newBuilder()
                    .addHeader("Authorization", "Bearer $bearer")
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                    .build().let { chain.proceed(it) }
            }
        }
    }

    single<HttpInterceptor>(named(Interceptor.DELETE_TOKEN)) {
        val useCase = get<DeleteAnilistTokenUseCase>()

        object : HttpInterceptor {
            override suspend fun intercept(request: HttpRequest, chain: HttpInterceptorChain) =
                chain.proceed(request).also { response ->
                    if (
                        response.statusCode == HTTP_BAD_REQUEST ||
                        response.statusCode == HTTP_UNAUTHORIZED
                    ) {
                        useCase()
                    }
                }
        }
    }

    single<HttpInterceptor>(named(Interceptor.LOGGING)) {
        LoggingInterceptor(
            log = { Logger.i(LogTag) { it } },
            level = if (KatanaBuildConfig.DEBUG) {
                LoggingInterceptor.Level.BODY
            } else {
                LoggingInterceptor.Level.NONE
            },
        )
    }

    factoryOf(::ReloadInterceptor) bind ApolloInterceptor::class
}

val coreRemoteModule = module {
    includes(apolloClientModule, apolloDatabaseModule(), apolloInterceptorsModule)
}

private const val ANILIST_BASE_URL = "https://graphql.anilist.co"

internal const val CACHE_DATABASE = "katana_data.db"
private const val CACHE_ID_KEY = "id"
private const val CACHE_TYPE_KEY = "__typename"

private const val HTTP_BAD_REQUEST = 400
private const val HTTP_UNAUTHORIZED = 401

private const val LogTag = "ApolloClient"
