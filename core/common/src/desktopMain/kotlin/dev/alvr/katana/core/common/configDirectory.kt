package dev.alvr.katana.core.common

import okio.Path
import okio.Path.Companion.toPath

private var configDirectory: Path? = null

fun configDirectory(): Path = configDirectory ?: run {
    val osName = System.getProperty("os.name").lowercase()
    val userHome = System.getProperty("user.home")

    when {
        osName.contains("win") -> {
            val appData = System.getenv("APPDATA")
            appData ?: "$userHome/AppData/Roaming"
        }

        osName.contains("mac") -> "$userHome/Library/Application Support"
        osName.contains("nix") ||
            osName.contains("nux") ||
            osName.contains("aix") -> "$userHome/.config"

        else -> throw UnsupportedOperationException("Unsupported OS: $osName")
    } + KatanaDir
}.toPath().also { configDirectory = it }

private const val KatanaDir = "/alvr.dev/Katana"
