package dev.alvr.katana.core.common.formatters

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import java.util.Locale
import kotlinx.datetime.LocalDateTime

internal class KatanaDateFormatsTest : FreeSpec({
    "nextEpisodeFormat" - {
        listOf(
            "en-US" to "Nov 13, 2024 - 2:15 PM",
            "es-ES" to "13 nov 2024 - 14:15",
            "de-DE" to "13.11.2024 - 14:15",
            "fr-FR" to "13 nov. 2024 - 14:15",
            "it-IT" to "13 nov 2024 - 14:15",
            "pt-PT" to "13/11/2024 - 14:15",
            "pt-BR" to "13 de nov. de 2024 - 14:15",
        ).forEach { (locale, expected) ->
            "should return a formatter with medium date style and short time style with $locale locale" {
                Locale.setDefault(Locale.forLanguageTag(locale))
                KatanaDateFormats.nextEpisodeFormat(NextEpisodeDate) shouldBe expected
            }
        }
    }
})

private val NextEpisodeDate = LocalDateTime(
    year = 2024,
    monthNumber = 11,
    dayOfMonth = 13,
    hour = 14,
    minute = 15,
)
