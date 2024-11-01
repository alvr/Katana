package dev.alvr.katana.core.common.locale

expect class KatanaPlatformLocale

expect value class KatanaLocale private constructor(private val locale: KatanaPlatformLocale) {
    companion object {
        fun default(): KatanaLocale
    }
}
