package dev.alvr.katana.ui.lists.viewmodel

import dev.alvr.katana.common.core.empty
import dev.alvr.katana.common.core.zero
import dev.alvr.katana.domain.lists.models.entries.CommonMediaEntry
import dev.alvr.katana.domain.lists.models.entries.MediaEntry
import dev.alvr.katana.domain.lists.models.lists.MediaList
import dev.alvr.katana.domain.lists.models.lists.MediaListEntry
import dev.alvr.katana.ui.lists.entities.MediaListItem
import java.time.LocalDate
import java.time.LocalDateTime

internal val animeListItem1 = MediaListItem.AnimeListItem(
    entryId = Int.zero,
    mediaId = Int.zero,
    title = "One Piece",
    score = Double.zero,
    format = MediaListItem.Format.OneShot,
    cover = String.empty,
    progress = 233,
    total = null,
    repeat = Int.zero,
    private = false,
    notes = String.empty,
    hiddenFromStatusLists = false,
    nextEpisode = null,
    startedAt = LocalDate.MAX,
    completedAt = LocalDate.MAX,
    updatedAt = LocalDateTime.MAX,
)

internal val animeListItem2 = MediaListItem.AnimeListItem(
    entryId = Int.zero,
    mediaId = Int.zero,
    title = String.empty,
    score = Double.zero,
    format = MediaListItem.Format.OneShot,
    cover = String.empty,
    progress = 234,
    total = null,
    repeat = Int.zero,
    private = true,
    notes = String.empty,
    hiddenFromStatusLists = false,
    nextEpisode = null,
    startedAt = LocalDate.MAX,
    completedAt = LocalDate.MAX,
    updatedAt = LocalDateTime.MAX,
)

internal val mangaListItem1 = MediaListItem.MangaListItem(
    entryId = Int.zero,
    mediaId = Int.zero,
    title = "One Piece",
    score = Double.zero,
    format = MediaListItem.Format.OneShot,
    cover = String.empty,
    progress = 233,
    total = Int.zero,
    volumesProgress = Int.zero,
    volumesTotal = Int.zero,
    repeat = Int.zero,
    private = false,
    notes = String.empty,
    hiddenFromStatusLists = false,
    startedAt = LocalDate.MAX,
    completedAt = LocalDate.MAX,
    updatedAt = LocalDateTime.MAX,
)

internal val mangaListItem2 = MediaListItem.MangaListItem(
    entryId = Int.zero,
    mediaId = Int.zero,
    title = String.empty,
    score = Double.zero,
    format = MediaListItem.Format.OneShot,
    cover = String.empty,
    progress = 234,
    volumesProgress = Int.zero,
    total = null,
    volumesTotal = null,
    repeat = Int.zero,
    private = true,
    notes = String.empty,
    hiddenFromStatusLists = false,
    startedAt = LocalDate.MAX,
    completedAt = LocalDate.MAX,
    updatedAt = LocalDateTime.MAX,
)

internal val animeMediaEntry1 = MediaListEntry(
    list = MediaList(
        id = Int.zero,
        score = Double.zero,
        progress = 233,
        progressVolumes = Int.zero,
        repeat = Int.zero,
        private = false,
        notes = String.empty,
        hiddenFromStatusLists = false,
        startedAt = LocalDate.MAX,
        completedAt = LocalDate.MAX,
        updatedAt = LocalDateTime.MAX,
    ),
    entry = MediaEntry.Anime(
        entry = CommonMediaEntry(
            id = Int.zero,
            title = "One Piece",
            coverImage = String.empty,
            format = CommonMediaEntry.Format.ONE_SHOT,
        ),
        episodes = null,
        nextEpisode = null,
    ),
)

internal val animeMediaEntry2 = MediaListEntry(
    list = MediaList(
        id = Int.zero,
        score = Double.zero,
        progress = 234,
        progressVolumes = Int.zero,
        repeat = Int.zero,
        private = true,
        notes = String.empty,
        hiddenFromStatusLists = false,
        startedAt = LocalDate.MAX,
        completedAt = LocalDate.MAX,
        updatedAt = LocalDateTime.MAX,
    ),
    entry = MediaEntry.Anime(
        entry = CommonMediaEntry(
            id = Int.zero,
            title = String.empty,
            coverImage = String.empty,
            format = CommonMediaEntry.Format.ONE_SHOT,
        ),
        episodes = null,
        nextEpisode = null,
    ),
)

internal val mangaMediaEntry1 = MediaListEntry(
    list = MediaList(
        id = Int.zero,
        score = Double.zero,
        progress = 233,
        progressVolumes = Int.zero,
        repeat = Int.zero,
        private = false,
        notes = String.empty,
        hiddenFromStatusLists = false,
        startedAt = LocalDate.MAX,
        completedAt = LocalDate.MAX,
        updatedAt = LocalDateTime.MAX,
    ),
    entry = MediaEntry.Manga(
        entry = CommonMediaEntry(
            id = Int.zero,
            title = "One Piece",
            coverImage = String.empty,
            format = CommonMediaEntry.Format.ONE_SHOT,
        ),
        chapters = Int.zero,
        volumes = Int.zero,
    ),
)

internal val mangaMediaEntry2 = MediaListEntry(
    list = MediaList(
        id = Int.zero,
        score = Double.zero,
        progress = 234,
        progressVolumes = Int.zero,
        repeat = Int.zero,
        private = true,
        notes = String.empty,
        hiddenFromStatusLists = false,
        startedAt = LocalDate.MAX,
        completedAt = LocalDate.MAX,
        updatedAt = LocalDateTime.MAX,
    ),
    entry = MediaEntry.Manga(
        entry = CommonMediaEntry(
            id = Int.zero,
            title = String.empty,
            coverImage = String.empty,
            format = CommonMediaEntry.Format.ONE_SHOT,
        ),
        chapters = null,
        volumes = null,
    ),
)
