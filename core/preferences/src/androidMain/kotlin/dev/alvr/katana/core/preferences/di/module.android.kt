package dev.alvr.katana.core.preferences.di

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.crypto.tink.Aead
import com.google.crypto.tink.KeyTemplates
import com.google.crypto.tink.RegistryConfiguration
import com.google.crypto.tink.aead.AeadConfig
import com.google.crypto.tink.integration.android.AndroidKeysetManager
import com.google.crypto.tink.integration.android.AndroidKeystore
import dev.alvr.katana.core.preferences.encrypt.AndroidPreferencesEncrypt
import dev.alvr.katana.core.preferences.encrypt.PreferencesEncrypt
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

private val aeadModule = module {
    single<Aead> {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            android23Aead()
        } else {
            androidCompatAead(androidApplication())
        }
    }
}

private val securerModule = module {
    factoryOf(::AndroidPreferencesEncrypt) bind PreferencesEncrypt::class
}

internal actual fun encryptionModule() = module {
    includes(aeadModule, securerModule)
}

private fun androidCompatAead(context: Context): Aead {
    AeadConfig.register()

    return AndroidKeysetManager.Builder()
        .withSharedPref(context, KEYSET_NAME, PREFERENCE_FILE)
        .withKeyTemplate(KeyTemplates.get(TEMPLATE_NAME))
        .withMasterKeyUri(MASTER_KEY_URI)
        .build()
        .keysetHandle
        .getPrimitive(RegistryConfiguration.get(), Aead::class.java)
}

@RequiresApi(Build.VERSION_CODES.M)
private fun android23Aead(): Aead {
    if (!AndroidKeystore.hasKey(KeyStoreAlias)) {
        AndroidKeystore.generateNewAes256GcmKey(KeyStoreAlias)
    }
    return AndroidKeystore.getAead(KeyStoreAlias)
}

private const val KEYSET_NAME = "master_keyset"
private const val PREFERENCE_FILE = "master_key_preference"
private const val MASTER_KEY_URI = "android-keystore://master_key"
private const val TEMPLATE_NAME = "AES256_GCM"

private const val KeyStoreAlias = "katana"
