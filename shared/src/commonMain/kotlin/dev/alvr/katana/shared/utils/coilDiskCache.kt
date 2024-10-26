package dev.alvr.katana.shared.utils

import coil3.PlatformContext
import okio.Path

internal expect fun PlatformContext.coilDiskCache(): Path

internal const val CoilImagesPath = "images_cache"
