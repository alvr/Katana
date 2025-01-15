package dev.alvr.katana.core.preferences

import androidx.datastore.core.okio.OkioSerializer
import dev.alvr.katana.core.preferences.serializers.PreferencesSerializer
import kotlinx.serialization.KSerializer

fun <T> KSerializer<T>.default(defaultValue: () -> T): OkioSerializer<T> =
    PreferencesSerializer(this, defaultValue)
