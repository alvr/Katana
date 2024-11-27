package dev.alvr.katana.core.common.formatters

import dev.alvr.katana.core.common.locale.KatanaLocale
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toNSTimeZone
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSDateFormatterMediumStyle
import platform.Foundation.NSDateFormatterShortStyle

internal actual typealias KatanaPlatformDateTimeFormatterBuilder = NSDateFormatter

internal actual fun dateTimeFormatterBuilder(
    block: KatanaPlatformDateTimeFormatterBuilder.() -> Unit,
): KatanaDateTimeFormatter = NSDateFormatter().apply {
    locale = KatanaLocale.default().platformLocale
    timeZone = TimeZone.currentSystemDefault().toNSTimeZone()
    block()
}.let { KatanaDateTimeFormatter(it) }

actual object KatanaDateFormats {
    actual val nextEpisodeFormat: KatanaDateTimeFormatter
        get() = dateTimeFormatterBuilder {
            dateStyle = NSDateFormatterMediumStyle
            timeStyle = NSDateFormatterShortStyle
        }
}
