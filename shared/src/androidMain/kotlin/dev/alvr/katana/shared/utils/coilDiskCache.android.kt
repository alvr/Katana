package dev.alvr.katana.shared.utils

import coil3.PlatformContext
import okio.Path.Companion.toOkioPath

internal actual fun PlatformContext.coilDiskCache() =
    cacheDir.resolve(CoilImagesPath).toOkioPath()
