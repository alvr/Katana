package dev.alvr.katana.core.preferences.di

import com.google.crypto.tink.Aead
import com.google.crypto.tink.InsecureSecretKeyAccess
import com.google.crypto.tink.KeysetHandle
import com.google.crypto.tink.RegistryConfiguration
import com.google.crypto.tink.TinkJsonProtoKeysetFormat
import com.google.crypto.tink.aead.AeadConfig
import com.google.crypto.tink.aead.PredefinedAeadParameters
import dev.alvr.katana.core.common.configDirectory
import org.koin.dsl.module

internal actual fun cipherModule() = module {
    single<Aead> {
        AeadConfig.register()

        val keysetFile = (configDirectory() / "katana_keyset.json").toFile()

        val keysetHandle = if (!keysetFile.exists()) {
            KeysetHandle.generateNew(PredefinedAeadParameters.AES256_GCM).also { handle ->
                val serialized = TinkJsonProtoKeysetFormat.serializeKeyset(handle, InsecureSecretKeyAccess.get())
                keysetFile.writeText(serialized)
            }
        } else {
            TinkJsonProtoKeysetFormat.parseKeyset(keysetFile.readText(), InsecureSecretKeyAccess.get())
        }

        keysetHandle.getPrimitive(RegistryConfiguration.get(), Aead::class.java)
    }
}
