package dev.alvr.katana.core.common.locale

import platform.Foundation.NSLocale
import platform.Foundation.currentLocale

actual typealias KatanaPlatformLocale = NSLocale

actual value class KatanaLocale private actual constructor(internal actual val platformLocale: KatanaPlatformLocale) {
    actual companion object {
        actual fun default(): KatanaLocale = KatanaLocale(NSLocale.currentLocale)
    }
}
