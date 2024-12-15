package dev.alvr.katana.core.preferences

import androidx.datastore.core.okio.OkioSerializer
import dev.alvr.katana.core.preferences.encrypt.PreferencesEncrypt
import dev.alvr.katana.core.preferences.serializers.EncryptedPreferencesSerializer
import dev.alvr.katana.core.preferences.serializers.PreferencesSerializer
import kotlinx.serialization.KSerializer

fun <T> KSerializer<T>.default(defaultValue: T): OkioSerializer<T> =
    PreferencesSerializer(this, defaultValue)

fun <T> KSerializer<T>.encrypted(defaultValue: T, securer: PreferencesEncrypt): OkioSerializer<T> =
    EncryptedPreferencesSerializer(securer, this, defaultValue)
