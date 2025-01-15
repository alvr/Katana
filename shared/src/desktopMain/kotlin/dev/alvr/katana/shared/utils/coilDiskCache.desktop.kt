package dev.alvr.katana.shared.utils

import coil3.PlatformContext
import dev.alvr.katana.core.common.configDirectory

internal actual fun PlatformContext.coilDiskCache() = configDirectory() / CoilImagesPath
