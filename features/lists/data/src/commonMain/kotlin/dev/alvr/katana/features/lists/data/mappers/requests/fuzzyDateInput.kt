package dev.alvr.katana.features.lists.data.mappers.requests

import dev.alvr.katana.core.remote.present
import dev.alvr.katana.core.remote.type.FuzzyDateInput
import kotlinx.datetime.LocalDate

internal fun LocalDate.toFuzzyDate() = FuzzyDateInput(
    year = year.present,
    month = monthNumber.present,
    day = dayOfMonth.present,
)
