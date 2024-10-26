package dev.alvr.katana.shared.utils

import coil3.PlatformContext
import okio.Path
import platform.Foundation.NSHomeDirectory

internal actual fun PlatformContext.coilDiskCache(): Path =
    NSHomeDirectory().toPath().resolve(CoilImagesPath)
