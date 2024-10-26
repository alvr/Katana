package dev.alvr.katana.shared

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import co.touchlab.kermit.DefaultFormatter
import co.touchlab.kermit.LogWriter
import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import co.touchlab.kermit.platformLogWriter
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.disk.DiskCache
import coil3.request.crossfade
import dev.alvr.katana.core.common.KatanaBuildConfig
import dev.alvr.katana.core.ui.theme.KatanaTheme
import dev.alvr.katana.core.ui.utils.noInsets
import dev.alvr.katana.core.ui.viewmodel.collectAsState
import dev.alvr.katana.features.home.ui.screen.home
import dev.alvr.katana.features.login.ui.screen.login
import dev.alvr.katana.shared.navigation.KatanaRootNavigator
import dev.alvr.katana.shared.navigation.rememberKatanaRootNavigator
import dev.alvr.katana.shared.utils.coilDiskCache
import dev.alvr.katana.shared.viewmodel.MainViewModel
import io.sentry.kotlin.multiplatform.PlatformOptionsConfiguration
import io.sentry.kotlin.multiplatform.Sentry
import io.sentry.kotlin.multiplatform.SentryLevel
import io.sentry.kotlin.multiplatform.protocol.Breadcrumb
import org.koin.compose.viewmodel.koinNavViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@Composable
fun Katana() {
    InitApp()

    KatanaTheme {
        KatanaApp()
    }
}

@Composable
@OptIn(KoinExperimentalAPI::class)
private fun KatanaApp(
    modifier: Modifier = Modifier,
    navigator: KatanaRootNavigator = rememberKatanaRootNavigator(),
    vm: MainViewModel = koinNavViewModel()
) {
    val state by vm.collectAsState()

    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets.noInsets,
    ) { paddingValues ->
        NavHost(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            navController = navigator.navController,
            startDestination = state.initialScreen.name,
        ) {
            login(loginNavigator = navigator)
            home(homeNavigator = navigator)
        }
    }
}

@Composable
private fun InitApp() {
    InitCoil()
    initSentry()
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
    } else {
        Logger.setLogWriters(SentryLogger(Severity.Error))
    }
}

internal expect fun sentryOptionsConfiguration(): PlatformOptionsConfiguration

private fun initSentry() {
    Sentry.initWithPlatformOptions(sentryOptionsConfiguration())
}

private class SentryLogger(private val minSeverity: Severity) : LogWriter() {
    private val Severity.sentryLevel
        get() = when (this) {
            Severity.Verbose -> SentryLevel.DEBUG
            Severity.Debug -> SentryLevel.DEBUG
            Severity.Info -> SentryLevel.INFO
            Severity.Warn -> SentryLevel.WARNING
            Severity.Error -> SentryLevel.ERROR
            Severity.Assert -> SentryLevel.FATAL
        }

    override fun isLoggable(tag: String, severity: Severity) =
        !KatanaBuildConfig.DEBUG && severity >= minSeverity

    override fun log(severity: Severity, message: String, tag: String, throwable: Throwable?) {
        if (throwable != null && severity >= minSeverity) {
            Sentry.addBreadcrumb(
                Breadcrumb(
                    level = severity.sentryLevel,
                    message = "$tag: $message",
                ),
            )
            Sentry.captureException(throwable)
        }
    }
}

private const val CoilMaxSizePercent = 0.02
