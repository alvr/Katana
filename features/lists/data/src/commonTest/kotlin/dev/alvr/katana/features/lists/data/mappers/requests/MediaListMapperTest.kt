package dev.alvr.katana.features.lists.data.mappers.requests

import com.apollographql.apollo.api.Optional
import dev.alvr.katana.core.common.empty
import dev.alvr.katana.core.common.zero
import dev.alvr.katana.core.remote.optional
import dev.alvr.katana.core.remote.present
import dev.alvr.katana.core.remote.type.FuzzyDateInput
import dev.alvr.katana.features.lists.data.MediaListEntriesMutation
import dev.alvr.katana.features.lists.domain.models.lists.MediaList
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.equals.shouldBeEqual
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

internal class MediaListMapperTest : FreeSpec({
    "a MediaList with all values present" {
        MediaList(
            id = Int.zero,
            score = Double.zero,
            progress = Int.zero,
            progressVolumes = Int.zero,
            repeat = Int.zero,
            private = false,
            notes = String.empty,
            hiddenFromStatusLists = false,
            startedAt = LocalDate(2022, 7, 20),
            completedAt = LocalDate(2022, 7, 20),
            updatedAt = Clock.System.now().toLocalDateTime(TimeZone.UTC),
        ).toMutation() shouldBeEqual MediaListEntriesMutation(
            id = Int.zero,
            score = Double.zero.present,
            progress = Int.zero.present,
            progressVolumes = Int.zero.present,
            repeat = Int.zero.present,
            private = false.present,
            notes = String.empty.present,
            hiddenFromStatusLists = false.optional,
            startedAt = FuzzyDateInput(
                year = Optional.Present(2022),
                month = Optional.Present(7),
                day = Optional.Present(20),
            ).present,
            completedAt = FuzzyDateInput(
                year = 2022.present,
                month = 7.present,
                day = 20.present,
            ).present,
        )
    }

    "a MediaList with nullable values" {
        MediaList(
            id = Int.zero,
            score = Double.zero,
            progress = Int.zero,
            progressVolumes = null,
            repeat = Int.zero,
            private = false,
            notes = String.empty,
            hiddenFromStatusLists = false,
            startedAt = null,
            completedAt = null,
            updatedAt = null,
        ).toMutation() shouldBeEqual MediaListEntriesMutation(
            id = Int.zero,
            score = Double.zero.present,
            progress = Int.zero.present,
            progressVolumes = null.optional,
            repeat = Int.zero.present,
            private = false.present,
            notes = String.empty.present,
            hiddenFromStatusLists = false.present,
            startedAt = null.optional,
            completedAt = null.optional,
        )
    }
})
