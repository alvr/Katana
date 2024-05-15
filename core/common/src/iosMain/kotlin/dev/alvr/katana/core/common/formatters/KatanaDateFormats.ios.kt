package dev.alvr.katana.core.common.formatters

import platform.Foundation.NSDateFormatter

internal actual typealias KatanaPlatformDateTimeFormatterBuilder = NSDateFormatter

internal actual fun dateTimeFormatterBuilder(
    block: KatanaPlatformDateTimeFormatterBuilder.() -> Unit
): KatanaDateTimeFormatter = NSDateFormatter().apply(block).let { KatanaDateTimeFormatter(it) }

actual object KatanaDateFormats {
    actual val nextEpisodeFormat: KatanaDateTimeFormatter get() = TODO()
}
