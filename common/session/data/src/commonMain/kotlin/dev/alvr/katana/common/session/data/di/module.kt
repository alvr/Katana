package dev.alvr.katana.common.session.data.di

import dev.alvr.katana.common.session.data.datastore.sessionDataStore
import dev.alvr.katana.common.session.data.entities.Session
import dev.alvr.katana.common.session.data.repositories.SessionRepositoryImpl
import dev.alvr.katana.common.session.data.sources.SessionLocalSource
import dev.alvr.katana.common.session.data.sources.SessionLocalSourceImpl
import dev.alvr.katana.common.session.domain.repositories.SessionRepository
import dev.alvr.katana.core.preferences.datastore.dataStoreFactory
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private val dataStoreModule = module {
    single(sessionDataStore) {
        dataStoreFactory(
            name = "session",
            serializer = Session.preferencesSerializer(),
            create = { Session() },
        )
    }
}

private val repositoriesModule = module {
    singleOf(::SessionRepositoryImpl) bind SessionRepository::class
}

private val sourcesModule = module {
    single<SessionLocalSource> {
        SessionLocalSourceImpl(
            store = get(sessionDataStore),
        )
    }
}

val commonSessionDataModule = module {
    includes(dataStoreModule, repositoriesModule, sourcesModule)
}
