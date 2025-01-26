package dev.alvr.katana.shared

import androidx.compose.runtime.Composable
import co.touchlab.kermit.DefaultFormatter
import co.touchlab.kermit.Logger
import co.touchlab.kermit.platformLogWriter
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.disk.DiskCache
import coil3.request.crossfade
import dev.alvr.katana.core.common.KatanaBuildConfig
import dev.alvr.katana.core.ui.theme.KatanaTheme
import dev.alvr.katana.shared.screens.Katana
import dev.alvr.katana.shared.utils.coilDiskCache
import org.koin.compose.KoinContext

@Composable
fun Katana() {
    InitApp()

    KoinContext {
        KatanaTheme {
            Katana()
        }
    }
}

@Composable
private fun InitApp() {
    InitCoil()
    initNapier()
}

@Composable
private fun InitCoil() {
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .crossfade(true)
            .diskCache {
                DiskCache.Builder()
                    .directory(context.coilDiskCache())
                    .maxSizePercent(CoilMaxSizePercent)
                    .build()
            }
            .build()
    }
}

private fun initNapier() {
    if (KatanaBuildConfig.DEBUG) {
        Logger.setLogWriters(platformLogWriter(DefaultFormatter))
    }
}

private const val CoilMaxSizePercent = 0.02
