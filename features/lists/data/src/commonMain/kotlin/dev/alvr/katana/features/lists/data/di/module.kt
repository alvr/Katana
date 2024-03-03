package dev.alvr.katana.features.lists.data.di

import dev.alvr.katana.features.lists.data.repositories.ListsRepositoryImpl
import dev.alvr.katana.features.lists.data.sources.CommonListsRemoteSource
import dev.alvr.katana.features.lists.data.sources.CommonListsRemoteSourceImpl
import dev.alvr.katana.features.lists.data.sources.anime.AnimeListsRemoteSource
import dev.alvr.katana.features.lists.data.sources.anime.AnimeListsRemoteSourceImpl
import dev.alvr.katana.features.lists.data.sources.manga.MangaListsRemoteSource
import dev.alvr.katana.features.lists.data.sources.manga.MangaListsRemoteSourceImpl
import dev.alvr.katana.features.lists.domain.repositories.ListsRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private val repositoriesModule = module {
    singleOf(::ListsRepositoryImpl).bind<ListsRepository>()
}

private val sourcesModule = module {
    singleOf(::CommonListsRemoteSourceImpl).bind<CommonListsRemoteSource>()
    singleOf(::AnimeListsRemoteSourceImpl).bind<AnimeListsRemoteSource>()
    singleOf(::MangaListsRemoteSourceImpl).bind<MangaListsRemoteSource>()
}

val dataRemoteListsModule = module {
    includes(repositoriesModule, sourcesModule)
}
