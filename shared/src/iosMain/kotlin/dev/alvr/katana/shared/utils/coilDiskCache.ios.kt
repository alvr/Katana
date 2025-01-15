package dev.alvr.katana.shared.utils

import coil3.PlatformContext
import okio.Path.Companion.toPath
import platform.Foundation.NSHomeDirectory

internal actual fun PlatformContext.coilDiskCache() =
    NSHomeDirectory().toPath() / CoilImagesPath
