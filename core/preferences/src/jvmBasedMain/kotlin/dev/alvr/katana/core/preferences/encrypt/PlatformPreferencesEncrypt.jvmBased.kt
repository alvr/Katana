package dev.alvr.katana.core.preferences.encrypt

import androidx.datastore.core.CorruptionException
import com.google.crypto.tink.Aead
import java.security.GeneralSecurityException

@Suppress("ACTUAL_WITHOUT_EXPECT")
internal actual typealias PlatformCipher = Aead

internal actual class PlatformPreferencesEncrypt actual constructor(
    private val cipher: PlatformCipher,
) : PreferencesEncrypt {
    override fun decrypt(input: ByteArray): ByteArray = operation("Aead decrypt") {
        cipher.decrypt(input, byteArrayOf())
    }

    override fun encrypt(input: ByteArray): ByteArray = operation("Aead encrypt") {
        cipher.encrypt(input, byteArrayOf())
    }

    private inline fun <R> operation(message: String, block: () -> R): R = try {
        block()
    } catch (e: GeneralSecurityException) {
        throw CorruptionException(message, e)
    }
}
