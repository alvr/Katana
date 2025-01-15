package dev.alvr.katana

import androidx.compose.ui.Alignment
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import dev.alvr.katana.app.resources.Res
import dev.alvr.katana.app.resources.app_name
import dev.alvr.katana.core.common.KatanaBuildConfig
import dev.alvr.katana.core.ui.resources.value
import dev.alvr.katana.shared.Katana
import dev.alvr.katana.shared.di.katanaModule
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

internal fun main() {
    startKoin {
        printLogger(if (KatanaBuildConfig.DEBUG) Level.DEBUG else Level.NONE)
        modules(katanaModule)
    }

    application {
        Window(
            resizable = true,
            onCloseRequest = ::exitApplication,
            title = Res.string.app_name.value,
            state = rememberWindowState(position = WindowPosition.Aligned(Alignment.Center)),
        ) {
            Katana()
        }
    }
}
