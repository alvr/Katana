package dev.alvr.katana.core.preferences.serializers

import dev.alvr.katana.core.preferences.encrypt.PreferencesEncrypt
import kotlinx.serialization.KSerializer

internal class EncryptedPreferencesSerializer<T>(
    private val encrypt: PreferencesEncrypt,
    serializer: KSerializer<T>,
    defaultValue: T,
) : PreferencesSerializer<T>(serializer, defaultValue) {
    override fun ByteArray.readFrom(): ByteArray = encrypt.decrypt(this)

    override fun ByteArray.writeTo(): ByteArray = encrypt.encrypt(this)
}
