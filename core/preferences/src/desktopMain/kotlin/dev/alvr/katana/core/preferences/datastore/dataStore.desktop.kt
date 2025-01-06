package dev.alvr.katana.core.preferences.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import dev.alvr.katana.core.common.configDirectory
import okio.FileSystem

internal actual val fileSystem = FileSystem.SYSTEM

internal actual fun dataStorePath(file: String) = configDirectory() / file

internal actual fun <T> replaceFile(create: (CorruptionException) -> T) = ReplaceFileCorruptionHandler(create)
