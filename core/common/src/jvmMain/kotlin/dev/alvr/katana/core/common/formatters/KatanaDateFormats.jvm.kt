package dev.alvr.katana.core.common.formatters

import dev.alvr.katana.core.common.locale.KatanaLocale
import java.time.format.DateTimeFormatterBuilder
import java.time.format.FormatStyle
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaZoneId

internal actual typealias KatanaPlatformDateTimeFormatterBuilder = DateTimeFormatterBuilder

internal actual fun dateTimeFormatterBuilder(
    block: KatanaPlatformDateTimeFormatterBuilder.() -> Unit
): KatanaDateTimeFormatter = DateTimeFormatterBuilder()
    .apply(block)
    .toFormatter(KatanaLocale.default().platformLocale)
    .withZone(TimeZone.currentSystemDefault().toJavaZoneId())
    .let { KatanaDateTimeFormatter(it) }

actual object KatanaDateFormats {
    actual val nextEpisodeFormat: KatanaDateTimeFormatter
        get() = dateTimeFormatterBuilder {
            appendLocalized(FormatStyle.MEDIUM, null)
            appendLiteral(" - ")
            appendLocalized(null, FormatStyle.SHORT)
        }
}
