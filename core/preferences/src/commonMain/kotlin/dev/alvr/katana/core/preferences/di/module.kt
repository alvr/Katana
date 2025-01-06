package dev.alvr.katana.core.preferences.di

import dev.alvr.katana.core.preferences.encrypt.PlatformPreferencesEncrypt
import dev.alvr.katana.core.preferences.encrypt.PreferencesEncrypt
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal expect fun cipherModule(): Module

private val encryptionModule = module {
    singleOf(::PlatformPreferencesEncrypt) bind PreferencesEncrypt::class
}

val corePreferencesModule = module {
    includes(cipherModule(), encryptionModule)
}
