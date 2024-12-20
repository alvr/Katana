package dev.alvr.katana.core.preferences.encrypt

import androidx.datastore.core.CorruptionException
import com.google.crypto.tink.Aead
import java.security.GeneralSecurityException

internal class AndroidPreferencesEncrypt(
    private val aead: Aead,
) : PreferencesEncrypt {
    override fun decrypt(input: ByteArray): ByteArray = operation("Aead decrypt") {
        aead.decrypt(input, byteArrayOf())
    }

    override fun encrypt(input: ByteArray): ByteArray = operation("Aead encrypt") {
        aead.encrypt(input, byteArrayOf())
    }

    private inline fun <R> operation(message: String, block: () -> R): R = try {
        block()
    } catch (e: GeneralSecurityException) {
        throw CorruptionException(message, e)
    }
}
