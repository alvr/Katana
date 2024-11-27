package dev.alvr.katana.core.common.formatters

import java.time.format.DateTimeFormatter
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toJavaLocalTime

@Suppress("ACTUAL_WITHOUT_EXPECT")
internal actual typealias KatanaPlatformDateTimeFormatter = DateTimeFormatter

@JvmInline
actual value class KatanaDateTimeFormatter internal constructor(
    private val formatter: KatanaPlatformDateTimeFormatter,
) {
    actual operator fun invoke(localDate: LocalDate): String =
        formatter.format(localDate.toJavaLocalDate())

    actual operator fun invoke(localTime: LocalTime): String =
        formatter.format(localTime.toJavaLocalTime())

    actual operator fun invoke(localDateTime: LocalDateTime): String =
        formatter.format(localDateTime.toJavaLocalDateTime())
}
