package dev.alvr.katana.core.remote.di

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.normalized.api.NormalizedCacheFactory
import com.apollographql.apollo.cache.normalized.sql.SqlNormalizedCacheFactory
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual fun apolloDatabaseModule(): Module = module {
    single<NormalizedCacheFactory> { SqlNormalizedCacheFactory(CACHE_DATABASE) }
}

// TODO: Wait for Sentry Apollo 4
// internal actual fun ApolloClient.Builder.sentryInterceptor() = sentryTracing(captureFailedRequests = true)
internal actual fun ApolloClient.Builder.sentryInterceptor() = this
