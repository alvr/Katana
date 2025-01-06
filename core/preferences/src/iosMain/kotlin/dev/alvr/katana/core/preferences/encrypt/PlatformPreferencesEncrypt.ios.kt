package dev.alvr.katana.core.preferences.encrypt

@Suppress("ACTUAL_WITHOUT_EXPECT")
internal actual class PlatformCipher

internal actual class PlatformPreferencesEncrypt actual constructor(
    cipher: PlatformCipher,
) : PreferencesEncrypt {
    override fun encrypt(input: ByteArray) = input
    override fun decrypt(input: ByteArray) = input
}
