package dev.alvr.katana.core.preferences.encrypt

internal expect interface PlatformCipher

internal expect class PlatformPreferencesEncrypt(cipher: PlatformCipher) : PreferencesEncrypt
