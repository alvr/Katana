package dev.alvr.katana.features.home.data.entities

import dev.alvr.katana.core.preferences.default
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class HomePreferences(
    @SerialName("wc")
    val welcomeCardVisible: Boolean = true,
) {
    internal companion object {
        fun preferencesSerializer() = serializer().default { HomePreferences() }
    }
}
