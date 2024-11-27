package dev.alvr.katana.core.common.formatters

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

internal expect class KatanaPlatformDateTimeFormatter

expect value class KatanaDateTimeFormatter internal constructor(private val formatter: KatanaPlatformDateTimeFormatter) {
    operator fun invoke(localDate: LocalDate): String
    operator fun invoke(localTime: LocalTime): String
    operator fun invoke(localDateTime: LocalDateTime): String
}
