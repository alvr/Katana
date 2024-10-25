package dev.alvr.katana.shared.utils

import coil3.PlatformContext
import okio.Path
import okio.Path.Companion.toOkioPath

internal actual fun PlatformContext.coilDiskCache(): Path =
    cacheDir.resolve(CoilImagesPath).toOkioPath()
