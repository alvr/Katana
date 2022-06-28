package dev.alvr.katana.domain.token.models

@JvmInline
value class AnilistToken(val token: String) {
    init {
        require(token.isNotBlank()) { "Token should not be empty." }
    }
}
