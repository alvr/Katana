package dev.alvr.katana.core.common.formatters

import dev.alvr.katana.core.common.noData
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.toNSDateComponents
import platform.Foundation.NSDateComponents
import platform.Foundation.NSDateComponentsFormatter
import platform.Foundation.NSDateFormatter

@Suppress("ACTUAL_WITHOUT_EXPECT")
internal actual typealias KatanaPlatformDateTimeFormatter = NSDateFormatter

actual value class KatanaDateTimeFormatter internal constructor(private val formatter: KatanaPlatformDateTimeFormatter) {
    actual operator fun invoke(localDate: LocalDate): String =
        formatter.stringFromDate(localDate.toNSDateComponents()) ?: String.noData

    actual operator fun invoke(localTime: LocalTime): String {
        val totalSeconds = localTime.toMillisecondOfDay() / 1000
        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60

        val components = NSDateComponents().apply {
            hour = hours.toLong()
            minute = minutes.toLong()
            second = seconds.toLong()
        }

        return formatter.stringFromDateComponents(components) ?: String.noData
    }

    actual operator fun invoke(localDateTime: LocalDateTime): String =
        formatter.stringFromDateComponents(localDateTime.toNSDateComponents()) ?: String.noData
}
