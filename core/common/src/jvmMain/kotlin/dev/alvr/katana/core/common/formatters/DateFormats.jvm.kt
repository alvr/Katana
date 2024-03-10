package dev.alvr.katana.core.common.formatters

import java.text.DateFormat
import java.util.Locale
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.byUnicodePattern

actual object DateFormats {
    actual val mediumFormat = LocalDate.Format {
        byUnicodePattern(get(DateFormat.MEDIUM))
    }

    private fun get(format: Int) = DateFormat.getDateInstance(format, Locale.getDefault()).toString()
}
